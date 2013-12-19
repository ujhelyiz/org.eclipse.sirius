/*******************************************************************************
 * Copyright (c) 2008, 2012 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.ui.tools.internal.wizards.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.ui.tools.api.views.ViewHelper;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

/**
 * Wizard to select representations from an Aird.
 * 
 * @author cbrun
 */
public class RepresentationsSelectionWizardPage extends WizardPage {

    private static final String SELECT_REPRESENTATIONS_TO_EXPORT = "Select Representations to export";

    /** The title of the page. */
    private static final String PAGE_TITLE = "Selection of representations to export";

    /** The page is completed. */
    private static final int CODE_OK = 0;

    /** The user has not selected one or more diagrams. */
    private static final int CODE_NO_SEL = 1;

    /** The user has selected an object that is not a diagram. */
    private static final int CODE_ERROR = 2;

    /** The composite control of the page. */
    private Composite pageComposite;

    /** The table viewer. */
    private CheckboxTreeViewer treeViewer;

    /** The filter. */

    private final Session root;

    private final Collection<DRepresentation> preselection;

    /**
     * Create a new <code>DescDiagramSelectionWizardPage</code>.
     * 
     * @param root
     *            the root object
     * @param representations
     *            the preselection.
     */
    public RepresentationsSelectionWizardPage(final Session root, final Collection<DRepresentation> representations) {
        super(PAGE_TITLE);
        this.setTitle(PAGE_TITLE);
        this.root = root;
        this.preselection = representations;
        if (preselection.size() > 0) {
            setPageComplete(true);
        }
        setMessage(SELECT_REPRESENTATIONS_TO_EXPORT, IMessageProvider.INFORMATION);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(final Composite parent) {
        initializeDialogUnits(parent);

        pageComposite = new Composite(parent, SWT.NONE);
        pageComposite.setLayout(new GridLayout());
        pageComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

        this.treeViewer = createTreeViewer(pageComposite);
        treeViewer.setInput(root);
        // treeViewer.addFilter(this.representationSelectionFilter);
        treeViewer.expandAll();
        treeViewer.collapseAll();
        setControl(pageComposite);

        for (final DRepresentation preselected : preselection) {
            this.treeViewer.setChecked(preselected, true);
        }

    }

    /**
     * Create the table viewer.
     * 
     * @param parent
     *            the parent composite.
     * @return the table viewer.
     */
    private CheckboxTreeViewer createTreeViewer(final Composite parent) {
        final ContainerCheckedTreeViewer viewer = new DescDiagramSelectionTreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

        final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        viewer.getControl().setLayoutData(gridData);
        viewer.getTree().setHeaderVisible(false);
        viewer.getTree().setLinesVisible(false);
        viewer.addCheckStateListener(new SiriusDiagramSelectionCheckStateListener());

        viewer.setContentProvider(new SessionContentProvider(this.root));
        viewer.setLabelProvider(new AdapterFactoryLabelProvider(ViewHelper.INSTANCE.createAdapterFactory()));
        return viewer;
    }

    /**
     * Change the page according to the new state of the selection.
     * 
     * @author cbrun
     */
    private class SiriusDiagramSelectionCheckStateListener implements ICheckStateListener {

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged(org.eclipse.jface.viewers.CheckStateChangedEvent)
         */
        public void checkStateChanged(final CheckStateChangedEvent event) {
            final int result = checkSelection(getSelectedElements());
            switch (result) {
            case CODE_OK:
            case CODE_NO_SEL:
                setMessage(SELECT_REPRESENTATIONS_TO_EXPORT, IMessageProvider.INFORMATION);
                setPageComplete(true);
                break;
            case CODE_ERROR:
                break;
            default:
                setMessage("Unknown code result", IMessageProvider.ERROR);
                setPageComplete(false);
                break;
            }
        }

    }

    private int checkSelection(final Collection<?> selectedItems) {
        int result = CODE_OK;
        if (selectedItems.isEmpty()) {
            result = CODE_NO_SEL;
        } else {
            final Iterator<?> iterItems = selectedItems.iterator();
            while (iterItems.hasNext()) {
                final Object next = iterItems.next();
                if (!(next instanceof DRepresentation)) {
                    result = CODE_ERROR;
                }
            }
        }
        return result;
    }

    /**
     * Selects only diagrams.
     * 
     * @author cbrun
     */
    private class DescDiagramSelectionTreeViewer extends ContainerCheckedTreeViewer {

        /**
         * Create a new <code>DescDiagramSelectionTreeViewer</code>.
         * 
         * @param parent
         *            the parent composite.
         */
        public DescDiagramSelectionTreeViewer(final Composite parent) {
            super(parent);
        }

        /**
         * Create a new <code>DescDiagramSelectionTreeViewer</code>.
         * 
         * @param parent
         *            the parent composite.
         * @param style
         *            the style.
         */
        public DescDiagramSelectionTreeViewer(final Composite parent, final int style) {
            super(parent, style);
        }

        /**
         * Create a new <code>DescDiagramSelectionTreeViewer</code>.
         * 
         * @param tree
         *            the tree.
         */
        public DescDiagramSelectionTreeViewer(final Tree tree) {
            super(tree);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.ui.dialogs.ContainerCheckedTreeViewer#doCheckStateChanged(java.lang.Object)
         */
        @Override
        protected void doCheckStateChanged(final Object element) {
            //
            // Check all diagrams that are under this element.
            final Widget item = findItem(element);
            if (item instanceof TreeItem) {
                final TreeItem treeItem = (TreeItem) item;
                if (!(element instanceof DRepresentation)) {
                    final boolean result = updateChildrenItems(treeItem, treeItem.getChecked());
                    if (result) {
                        treeItem.setGrayed(true);
                    } else {
                        treeItem.setGrayed(false);
                    }
                    treeItem.setChecked(result);
                }
                updateParentItems(treeItem);
            }

        }

        /**
         * Updates the check / gray state of all parent items
         */
        private void updateParentItems(final TreeItem item) {
            if (item != null && !(item.getData() instanceof DRepresentation)) {
                final Item[] children = getChildren(item);
                boolean containsChecked = false;
                for (Item element : children) {
                    final TreeItem curr = (TreeItem) element;
                    containsChecked = containsChecked || curr.getChecked();
                }
                item.setChecked(containsChecked);
                item.setGrayed(containsChecked);
            }
            if (item != null) {
                updateParentItems(item.getParentItem());
            }
        }

        /**
         * Updates the check state of all created children
         * 
         * @return <code>true</code> if an element as been checked.
         */
        private boolean updateChildrenItems(final TreeItem parent, final boolean state) {
            boolean result = false;
            final Item[] children = getChildren(parent);
            for (Item element : children) {
                final TreeItem curr = (TreeItem) element;
                if (curr.getData() instanceof DRepresentation && ((curr.getChecked() != state) || curr.getGrayed())) {
                    curr.setChecked(state);
                    curr.setGrayed(false);
                    result = result || state;
                    result = result || updateChildrenItems(curr, state);
                } else if (curr.getData() != null && ((curr.getChecked() != state) || curr.getGrayed())) {
                    final boolean childrenResult = updateChildrenItems(curr, state);
                    if (childrenResult) {
                        curr.setChecked(true);
                        curr.setGrayed(true);
                    } else {
                        curr.setChecked(false);
                        curr.setGrayed(false);
                    }
                    result = result || childrenResult;
                }
            }
            return result;
        }
    }

    /**
     * Return all selected elements.
     * 
     * @return all selected elements.
     */
    public Collection<DRepresentation> getSelectedElements() {
        final Collection<DRepresentation> result = new HashSet<DRepresentation>();
        result.addAll((Collection<? extends DRepresentation>) Arrays.asList(treeViewer.getCheckedElements()));
        result.removeAll(Arrays.asList(treeViewer.getGrayedElements()));
        return result;
    }

    private static final class SessionContentProvider implements ITreeContentProvider {

        private static Object[] empty = new Object[0];

        /** The semantic EMF content provider. */
        private final AdapterFactoryContentProvider semanticProvider;

        private final Session session;

        /**
         * Create a new <code>SemanticDViewContentProvider</code> with the
         * specified analysis.
         * 
         * @param functionnalAnalysis
         *            the analysis to browse.
         */
        public SessionContentProvider(final Session session) {
            this.session = session;
            final AdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
            this.semanticProvider = new AdapterFactoryContentProvider(adapterFactory);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
         */
        public Object[] getChildren(final Object parentElement) {
            Object[] children = empty;
            if (parentElement instanceof Session) {
                children = ((Session) parentElement).getSemanticResources().toArray();
            } else if (parentElement instanceof Resource) {
                // Test Resource before EObject because with CDO a CDOResource
                // is a EObject
                children = ((Resource) parentElement).getContents().toArray();
            } else if (parentElement instanceof EObject && !(parentElement instanceof DRepresentation)) {
                final EObject parent = (EObject) parentElement;
                final Collection<DRepresentation> representations = this.findRepresentations(parent);
                Object[] semantic = this.semanticProvider.getChildren(parentElement);
                semantic = filtersSemanticFromAnotherResource(parent.eResource(), semantic);
                final Object[] result = new Object[representations.size() + semantic.length];
                int i = 0;
                final Iterator<DRepresentation> iterRepresentation = representations.iterator();
                while (iterRepresentation.hasNext()) {
                    result[i++] = iterRepresentation.next();
                }
                System.arraycopy(semantic, 0, result, representations.size(), semantic.length);
                children = result;
            }
            return children;
        }

        private Object[] filtersSemanticFromAnotherResource(final Resource resource, final Object[] semantic) {
            final Collection<Object> filtered = new ArrayList<Object>();
            for (final Object object : semantic) {
                filtered.add(object);
            }
            for (final Object object : semantic) {
                if (object instanceof EObject && ((EObject) object).eResource() != null) {
                    if (resource != ((EObject) object).eResource() && session.getSemanticResources().contains(((EObject) object).eResource())) {
                        filtered.remove(object);
                    }
                }
            }

            return filtered.toArray();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
         */
        public Object getParent(final Object element) {
            if (element instanceof EObject) {
                final EObject current = (EObject) element;
                final EObject parent = current instanceof DRepresentation ? ((DSemanticDecorator) current).getTarget() : current.eContainer();
                return parent;
            }
            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
         */
        public boolean hasChildren(final Object element) {
            return getChildren(element).length > 0;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         */
        public Object[] getElements(final Object inputElement) {
            return getChildren(inputElement);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        public void dispose() {
            this.semanticProvider.dispose();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         */
        public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
            // empty
        }

        /**
         * Return all the diagrams for the specified viewpoint.
         * 
         * @param semanticElement
         *            the parent semantic element.
         * @return all the diagrams for the specified viewpoint.
         */
        private Collection<DRepresentation> findRepresentations(final EObject semanticElement) {
            if (semanticElement == null) {
                return Collections.emptySet();
            }
            return DialectManager.INSTANCE.getRepresentations(semanticElement, session);
        }
    }

}
