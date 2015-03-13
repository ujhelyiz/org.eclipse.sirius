/*******************************************************************************
 * Copyright (c) 2012, 2015 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.internal.session.danalysis;

import java.util.Collection;
import java.util.Map.Entry;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.viewpoint.DAnalysis;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.ViewpointPackage;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * A {@link ResourceSetListener} to update the
 * {@link ViewpointPackage#DANALYSIS__MODELS} feature according changes in
 * representations resources. This class also contains some methods to load + *
 * resources and detects new semantic resources.
 * 
 * @author <a href="mailto:esteban.dugueperoux@obeo.fr">Esteban Dugueperoux</a>
 */
public class DAnalysisRefresher extends ResourceSetListenerImpl implements ResourceSetListener {

    private Session session;

    /**
     * Default constructor.
     * 
     * @param session
     *            the {@link Session} on which listens model changes.
     */
    public DAnalysisRefresher(Session session) {
        this.session = Preconditions.checkNotNull(session);
    }

    /**
     * Add this listener on the editingDomain.
     */
    public void initialize() {
        session.getTransactionalEditingDomain().addResourceSetListener(this);
    }

    /**
     * Overridden to say that we are interested only in added elements.
     * 
     * {@inheritDoc}
     */
    @Override
    public NotificationFilter getFilter() {
        return NotificationFilter.createEventTypeFilter(Notification.ADD);
    }

    @Override
    public boolean isPrecommitOnly() {
        return true;
    }

    @Override
    public boolean isAggregatePrecommitListener() {
        return true;
    }

    /**
     * Overridden to update the {@link DAnalysis#getModels()} reference
     * according to new added {@link DSemanticDecorator}.
     * 
     * {@inheritDoc}
     */
    @Override
    public Command transactionAboutToCommit(ResourceSetChangeEvent event) throws RollbackException {
        Multimap<DAnalysis, EObject> referencedSemanticModels = getRootSemanticResourceEltsPerRepresentationsResource(event.getNotifications());
        CompoundCommand result = new CompoundCommand();
        for (Entry<DAnalysis, Collection<EObject>> entry : referencedSemanticModels.asMap().entrySet()) {
            DAnalysis analysis = entry.getKey();
            Collection<EObject> rootSemanticResourceElts = entry.getValue();
            if (!analysis.getModels().containsAll(rootSemanticResourceElts)) {
                rootSemanticResourceElts.removeAll(analysis.getModels());
                result.append(AddCommand.create(event.getEditingDomain(), analysis, ViewpointPackage.Literals.DANALYSIS__MODELS, rootSemanticResourceElts));
            }
        }
        return result;
    }

    /**
     * Remove this listener from the editingDomain.
     */
    public void dispose() {
        session.getTransactionalEditingDomain().removeResourceSetListener(this);
        session = null;
    }

    /**
     * Get for each {@link DAnalysis} impacted a set of semantic resource root
     * element whose semantic resource content is referenced by a
     * {@link DSemanticDecorator} from the specified collection of
     * {@link Notification}s.
     * 
     * @param notifications
     *            the specified collection of {@link Notification}
     * @return a map associating for each {@link DAnalysis} semantic resource
     *         root element
     */
    private Multimap<DAnalysis, EObject> getRootSemanticResourceEltsPerRepresentationsResource(Collection<Notification> notifications) {
        Multimap<DAnalysis, EObject> referencedSemanticModels = HashMultimap.create();
        for (Notification notification : notifications) {
            if (Notification.ADD == notification.getEventType() && notification.getNewValue() instanceof DSemanticDecorator) {
                DSemanticDecorator decorator = (DSemanticDecorator) notification.getNewValue();
                Resource representationsResource = decorator.eResource();
                if (decorator.getTarget() != null && representationsResource != null) {
                    DAnalysis analysis = (DAnalysis) representationsResource.getContents().get(0);
                    Resource targetResource = decorator.getTarget().eResource();
                    registerNewReferencedResource(referencedSemanticModels, analysis, targetResource);
                }
            }
        }
        return referencedSemanticModels;
    }

    private void registerNewReferencedResource(Multimap<DAnalysis, EObject> referencedSemanticModels, DAnalysis analysis, Resource semanticResource) {
        if (semanticResource != null) {
            EObject rootSemanticResourceElement = semanticResource.getContents().get(0);
            referencedSemanticModels.put(analysis, rootSemanticResourceElement);
        }
    }
}
