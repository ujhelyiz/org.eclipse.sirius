/**
 * Copyright (c) 2007, 2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *
 */
package org.eclipse.sirius.viewpoint.description.tool.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.business.api.query.IdentifiedElementQuery;
import org.eclipse.sirius.common.tools.api.util.StringUtil;
import org.eclipse.sirius.viewpoint.description.IdentifiedElement;
import org.eclipse.sirius.viewpoint.description.tool.ExternalJavaAction;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.eclipse.sirius.viewpoint.description.tool.ToolPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.viewpoint.description.tool.ExternalJavaAction}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class ExternalJavaActionItemProvider extends MenuItemDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ExternalJavaActionItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addIdPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Id feature. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_ExternalJavaAction_id_feature"), //$NON-NLS-1$
                        getString("_UI_ExternalJavaAction_id_description"), //$NON-NLS-1$
                        ToolPackage.Literals.EXTERNAL_JAVA_ACTION__ID, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, getString("_UI_GeneralPropertyCategory"), //$NON-NLS-1$
                        null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to
     * deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand},
     * {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in
     * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS);
            childrenFeatures.add(ToolPackage.Literals.EXTERNAL_JAVA_ACTION__PARAMETERS);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper
        // feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns ExternalJavaAction.gif. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/ExternalJavaAction")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @not-generated
     */
    @Override
    public String getText(Object object) {
        String label = new IdentifiedElementQuery((IdentifiedElement) object).getLabel();
        return StringUtil.isEmpty(label) ? getString("_UI_ExternalJavaAction_type") : getString("_UI_ExternalJavaAction_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to
     * update any cached children and by creating a viewer notification, which
     * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(ExternalJavaAction.class)) {
        case ToolPackage.EXTERNAL_JAVA_ACTION__ID:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case ToolPackage.EXTERNAL_JAVA_ACTION__SUB_MODEL_OPERATIONS:
        case ToolPackage.EXTERNAL_JAVA_ACTION__PARAMETERS:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing the children that can be created under this object. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createExternalJavaAction()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createExternalJavaActionCall()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createCreateInstance()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createChangeContext()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createSetValue()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createSetObject()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createUnset()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createMoveElement()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createRemoveElement()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createFor()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createIf()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createDeleteView()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.CONTAINER_MODEL_OPERATION__SUB_MODEL_OPERATIONS, ToolFactory.eINSTANCE.createSwitch()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.EXTERNAL_JAVA_ACTION__PARAMETERS, ToolFactory.eINSTANCE.createExternalJavaActionParameter()));
    }

}
