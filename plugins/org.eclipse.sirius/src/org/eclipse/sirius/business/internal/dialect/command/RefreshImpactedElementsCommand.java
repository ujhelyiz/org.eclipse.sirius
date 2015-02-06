/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.internal.dialect.command;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.internal.dialect.DialectServices2;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;

/**
 * This command performs a refresh only for impacted elements.
 * 
 * @author Florian Barbin
 */
public class RefreshImpactedElementsCommand extends RecordingCommand {

    private Collection<Notification> notifications;

    private Collection<DRepresentation> representations;

    private IProgressMonitor monitor;

    /**
     * Construct a new instance.
     * 
     * @param domain
     *            the editing domain.
     * @param monitor
     *            a progress monitor.
     * @param representationsToRefresh
     *            the representations to refresh.
     * @param notifications
     *            the notifications that concern this refresh.
     */
    public RefreshImpactedElementsCommand(TransactionalEditingDomain domain, IProgressMonitor monitor, Collection<DRepresentation> representationsToRefresh, Collection<Notification> notifications) {
        super(domain, "Refresh impacted representation elements");
        this.monitor = monitor;
        this.representations = representationsToRefresh;
        this.notifications = notifications;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doExecute() {
        if (monitor == null) {
            monitor = new NullProgressMonitor();
        }

        for (DRepresentation representation : representations) {
            if (safeRefresh(representation) && DialectManager.INSTANCE instanceof DialectServices2) {
                ((DialectServices2) DialectManager.INSTANCE).refreshImpactedElements(representation, notifications, monitor);
            }
        }
    }

    private boolean safeRefresh(DRepresentation representation) {
        boolean safeRefresh = representation != null;
        if (representation instanceof DSemanticDecorator) {
            safeRefresh = ((DSemanticDecorator) representation).getTarget() != null;
        }
        return safeRefresh;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
     */
    @Override
    public boolean canExecute() {
        return representations.size() != 0;
    }

}
