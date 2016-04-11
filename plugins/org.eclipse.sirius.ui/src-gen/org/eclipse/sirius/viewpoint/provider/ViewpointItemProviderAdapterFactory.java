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
package org.eclipse.sirius.viewpoint.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.sirius.viewpoint.util.ViewpointAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support
 * Viewers. The adapters generated by this factory convert EMF adapter
 * notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}. The
 * adapters also support Eclipse property sheets. Note that most of the adapters
 * are shared among multiple instances. <!-- begin-user-doc --> <!--
 * end-user-doc -->
 *
 * @generated
 */
public class ViewpointItemProviderAdapterFactory extends ViewpointAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
    /**
     * This keeps track of the root adapter factory that delegates to this
     * adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ComposedAdapterFactory parentAdapterFactory;

    /**
     * This is used to implement
     * {@link org.eclipse.emf.edit.provider.IChangeNotifier}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected IChangeNotifier changeNotifier = new ChangeNotifier();

    /**
     * This keeps track of all the supported types checked by
     * {@link #isFactoryForType isFactoryForType}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected Collection<Object> supportedTypes = new ArrayList<Object>();

    /**
     * This constructs an instance. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    public ViewpointItemProviderAdapterFactory() {
        supportedTypes.add(IEditingDomainItemProvider.class);
        supportedTypes.add(IStructuredItemContentProvider.class);
        supportedTypes.add(ITreeItemContentProvider.class);
        supportedTypes.add(IItemLabelProvider.class);
        supportedTypes.add(IItemPropertySource.class);
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.DAnalysis} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DAnalysisItemProvider dAnalysisItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.viewpoint.DAnalysis}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDAnalysisAdapter() {
        if (dAnalysisItemProvider == null) {
            dAnalysisItemProvider = new DAnalysisItemProvider(this);
        }

        return dAnalysisItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.DView} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DViewItemProvider dViewItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.viewpoint.DView}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDViewAdapter() {
        if (dViewItemProvider == null) {
            dViewItemProvider = new DViewItemProvider(this);
        }

        return dViewItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.MetaModelExtension} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected MetaModelExtensionItemProvider metaModelExtensionItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.viewpoint.MetaModelExtension}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createMetaModelExtensionAdapter() {
        if (metaModelExtensionItemProvider == null) {
            metaModelExtensionItemProvider = new MetaModelExtensionItemProvider(this);
        }

        return metaModelExtensionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.Decoration} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DecorationItemProvider decorationItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.viewpoint.Decoration}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDecorationAdapter() {
        if (decorationItemProvider == null) {
            decorationItemProvider = new DecorationItemProvider(this);
        }

        return decorationItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.DAnalysisCustomData} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DAnalysisCustomDataItemProvider dAnalysisCustomDataItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.viewpoint.DAnalysisCustomData}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDAnalysisCustomDataAdapter() {
        if (dAnalysisCustomDataItemProvider == null) {
            dAnalysisCustomDataItemProvider = new DAnalysisCustomDataItemProvider(this);
        }

        return dAnalysisCustomDataItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.LabelStyle} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected LabelStyleItemProvider labelStyleItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.viewpoint.LabelStyle}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createLabelStyleAdapter() {
        if (labelStyleItemProvider == null) {
            labelStyleItemProvider = new LabelStyleItemProvider(this);
        }

        return labelStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.DAnalysisSessionEObject} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DAnalysisSessionEObjectItemProvider dAnalysisSessionEObjectItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.viewpoint.DAnalysisSessionEObject}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDAnalysisSessionEObjectAdapter() {
        if (dAnalysisSessionEObjectItemProvider == null) {
            dAnalysisSessionEObjectItemProvider = new DAnalysisSessionEObjectItemProvider(this);
        }

        return dAnalysisSessionEObjectItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.SessionManagerEObject} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected SessionManagerEObjectItemProvider sessionManagerEObjectItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.viewpoint.SessionManagerEObject}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createSessionManagerEObjectAdapter() {
        if (sessionManagerEObjectItemProvider == null) {
            sessionManagerEObjectItemProvider = new SessionManagerEObjectItemProvider(this);
        }

        return sessionManagerEObjectItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.DFile} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DFileItemProvider dFileItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.viewpoint.DFile}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDFileAdapter() {
        if (dFileItemProvider == null) {
            dFileItemProvider = new DFileItemProvider(this);
        }

        return dFileItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.DResourceContainer} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DResourceContainerItemProvider dResourceContainerItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.viewpoint.DResourceContainer}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDResourceContainerAdapter() {
        if (dResourceContainerItemProvider == null) {
            dResourceContainerItemProvider = new DResourceContainerItemProvider(this);
        }

        return dResourceContainerItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.DProject} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DProjectItemProvider dProjectItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.viewpoint.DProject}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDProjectAdapter() {
        if (dProjectItemProvider == null) {
            dProjectItemProvider = new DProjectItemProvider(this);
        }

        return dProjectItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.DFolder} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DFolderItemProvider dFolderItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.viewpoint.DFolder}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDFolderAdapter() {
        if (dFolderItemProvider == null) {
            dFolderItemProvider = new DFolderItemProvider(this);
        }

        return dFolderItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.DModel} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DModelItemProvider dModelItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.viewpoint.DModel}
     * . <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDModelAdapter() {
        if (dModelItemProvider == null) {
            dModelItemProvider = new DModelItemProvider(this);
        }

        return dModelItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.BasicLabelStyle} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected BasicLabelStyleItemProvider basicLabelStyleItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.viewpoint.BasicLabelStyle}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createBasicLabelStyleAdapter() {
        if (basicLabelStyleItemProvider == null) {
            basicLabelStyleItemProvider = new BasicLabelStyleItemProvider(this);
        }

        return basicLabelStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.viewpoint.UIState} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected UIStateItemProvider uiStateItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.viewpoint.UIState}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createUIStateAdapter() {
        if (uiStateItemProvider == null) {
            uiStateItemProvider = new UIStateItemProvider(this);
        }

        return uiStateItemProvider;
    }

    /**
     * This returns the root adapter factory that contains this factory. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ComposeableAdapterFactory getRootAdapterFactory() {
        return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
    }

    /**
     * This sets the composed adapter factory that contains this factory. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
        this.parentAdapterFactory = parentAdapterFactory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object type) {
        return supportedTypes.contains(type) || super.isFactoryForType(type);
    }

    /**
     * This implementation substitutes the factory itself as the key for the
     * adapter. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter adapt(Notifier notifier, Object type) {
        return super.adapt(notifier, this);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object adapt(Object object, Object type) {
        if (isFactoryForType(type)) {
            Object adapter = super.adapt(object, type);
            if (!(type instanceof Class<?>) || (((Class<?>) type).isInstance(adapter))) {
                return adapter;
            }
        }

        return null;
    }

    /**
     * This adds a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void addListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.addListener(notifyChangedListener);
    }

    /**
     * This removes a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void removeListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.removeListener(notifyChangedListener);
    }

    /**
     * This delegates to {@link #changeNotifier} and to
     * {@link #parentAdapterFactory}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    @Override
    public void fireNotifyChanged(Notification notification) {
        changeNotifier.fireNotifyChanged(notification);

        if (parentAdapterFactory != null) {
            parentAdapterFactory.fireNotifyChanged(notification);
        }
    }

    /**
     * This disposes all of the item providers created by this factory. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void dispose() {
        if (dAnalysisItemProvider != null) {
            dAnalysisItemProvider.dispose();
        }
        if (dViewItemProvider != null) {
            dViewItemProvider.dispose();
        }
        if (metaModelExtensionItemProvider != null) {
            metaModelExtensionItemProvider.dispose();
        }
        if (decorationItemProvider != null) {
            decorationItemProvider.dispose();
        }
        if (dAnalysisCustomDataItemProvider != null) {
            dAnalysisCustomDataItemProvider.dispose();
        }
        if (labelStyleItemProvider != null) {
            labelStyleItemProvider.dispose();
        }
        if (dAnalysisSessionEObjectItemProvider != null) {
            dAnalysisSessionEObjectItemProvider.dispose();
        }
        if (sessionManagerEObjectItemProvider != null) {
            sessionManagerEObjectItemProvider.dispose();
        }
        if (dFileItemProvider != null) {
            dFileItemProvider.dispose();
        }
        if (dResourceContainerItemProvider != null) {
            dResourceContainerItemProvider.dispose();
        }
        if (dProjectItemProvider != null) {
            dProjectItemProvider.dispose();
        }
        if (dFolderItemProvider != null) {
            dFolderItemProvider.dispose();
        }
        if (dModelItemProvider != null) {
            dModelItemProvider.dispose();
        }
        if (basicLabelStyleItemProvider != null) {
            basicLabelStyleItemProvider.dispose();
        }
        if (uiStateItemProvider != null) {
            uiStateItemProvider.dispose();
        }
    }

}
