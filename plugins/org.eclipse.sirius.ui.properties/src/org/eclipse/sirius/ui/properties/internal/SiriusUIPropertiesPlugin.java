/*******************************************************************************
 * Copyright (c) 2015, 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.ui.properties.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.eef.EefPackage;
import org.eclipse.eef.common.api.AbstractEEFEclipsePlugin;
import org.eclipse.eef.ide.api.extensions.AbstractRegistryEventListener;
import org.eclipse.eef.ide.api.extensions.IItemDescriptor;
import org.eclipse.eef.ide.api.extensions.IItemRegistry;
import org.eclipse.eef.ide.api.extensions.impl.DescriptorRegistryEventListener;
import org.eclipse.eef.ide.api.extensions.impl.ItemRegistry;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.sirius.ui.properties.api.DefaultDescriptionConverter;
import org.eclipse.sirius.ui.properties.api.DefaultDescriptionWithInitialOperationConverter;
import org.eclipse.sirius.ui.properties.api.IDescriptionConverter;
import org.eclipse.sirius.ui.properties.api.IDescriptionLinkResolver;
import org.eclipse.sirius.ui.properties.internal.tabprovider.PropertiesDescriptionConverterSwitch;
import org.eclipse.sirius.ui.properties.internal.tabprovider.PropertyValidationRuleLinkResolver;
import org.eclipse.sirius.ui.properties.internal.tabprovider.SemanticValidationRuleDescriptionConverter;
import org.eclipse.sirius.viewpoint.description.validation.RuleAudit;
import org.eclipse.sirius.viewpoint.description.validation.SemanticValidationRule;
import org.eclipse.sirius.viewpoint.description.validation.ValidationFix;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

/**
 * The plugin for <code>org.eclipse.sirius.ui.properties</code>.
 * 
 * @author pcdavid
 * @author sbegaudeau
 */
public class SiriusUIPropertiesPlugin extends EMFPlugin {
    /**
     * The identifier of the plugin.
     */
    public static final String PLUGIN_ID = "org.eclipse.sirius.ui.properties"; //$NON-NLS-1$

    /**
     * The sole instance of the plugin.
     */
    public static final SiriusUIPropertiesPlugin INSTANCE = new SiriusUIPropertiesPlugin();

    /**
     * The sole instance of the bundle activator.
     */
    private static Implementation plugin;

    /**
     * The constructor.
     */
    public SiriusUIPropertiesPlugin() {
        super(new ResourceLocator[0]);
    }

    @Override
    public ResourceLocator getPluginResourceLocator() {
        return plugin;
    }

    /**
     * Returns the singleton instance of the Eclipse plugin.
     * 
     * @return the singleton instance.
     */
    public static Implementation getPlugin() {
        return plugin;
    }

    /**
     * The bundle activator.
     * 
     * @author sbegaudeau
     */
    public static class Implementation extends AbstractEEFEclipsePlugin {
        /**
         * The name of the extension point for the {@link IDescriptionConverter}
         * .
         */
        private static final String DESCRIPTION_CONVERTER_EXTENSION_POINT = "descriptionConverter"; //$NON-NLS-1$

        /**
         * The name of the extension point for the
         * {@link IDescriptionLinkResolver}.
         */
        private static final String DESCRIPTION_LINK_RESOLVER_EXTENSION_POINT = "descriptionLinkResolver"; //$NON-NLS-1$

        /**
         * The {@link IItemRegistry} used to retrieve the
         * {@link IDescriptionConverter}.
         */
        private IItemRegistry<IDescriptionConverter> descriptionConverterRegistry;

        /**
         * The {@link IItemRegistry} used to retrieve the
         * {@link IDescriptionLinkResolver}.
         */
        private IItemRegistry<IDescriptionLinkResolver> descriptionLinkResolverRegistry;

        /**
         * The extension registry listener for the {@link IDescriptionConverter}
         * .
         */
        private AbstractRegistryEventListener descriptionConverterListener;

        /**
         * The extension registry listener for the
         * {@link IDescriptionLinkResolver}.
         */
        private AbstractRegistryEventListener descriptionLinkResolverListener;

        /**
         * Storage for preferences.
         */
        private ScopedPreferenceStore preferenceStore;

        /**
         * The property change listener.
         */
        private IPropertyChangeListener propertyChangeListener;

        /**
         * The constructor.
         */
        public Implementation() {
            super(PLUGIN_ID);
            SiriusUIPropertiesPlugin.plugin = this;
            startPropertiesPreferencesManagement();
        }

        @Override
        public void start(BundleContext context) throws Exception {
            super.start(context);

            IExtensionRegistry registry = Platform.getExtensionRegistry();
            this.descriptionConverterRegistry = new ItemRegistry<>();
            this.descriptionConverterListener = new DescriptorRegistryEventListener<>(PLUGIN_ID, DESCRIPTION_CONVERTER_EXTENSION_POINT, this.descriptionConverterRegistry);
            registry.addListener(this.descriptionConverterListener, PLUGIN_ID + '.' + DESCRIPTION_CONVERTER_EXTENSION_POINT);
            this.descriptionConverterListener.readRegistry(registry);

            this.descriptionLinkResolverRegistry = new ItemRegistry<>();
            this.descriptionLinkResolverListener = new DescriptorRegistryEventListener<>(PLUGIN_ID, DESCRIPTION_LINK_RESOLVER_EXTENSION_POINT, this.descriptionLinkResolverRegistry);
            registry.addListener(this.descriptionLinkResolverListener, PLUGIN_ID + '.' + DESCRIPTION_LINK_RESOLVER_EXTENSION_POINT);
            this.descriptionLinkResolverListener.readRegistry(registry);
        }

        @Override
        public void stop(BundleContext context) throws Exception {
            IExtensionRegistry registry = Platform.getExtensionRegistry();
            registry.removeListener(this.descriptionConverterListener);
            registry.removeListener(this.descriptionLinkResolverListener);

            this.descriptionConverterListener = null;
            this.descriptionConverterRegistry = null;
            this.descriptionLinkResolverListener = null;
            this.descriptionLinkResolverRegistry = null;

            stopPropertiesPreferencesManagement();

            super.stop(context);
        }

        /**
         * Returns the description converter used to create the EEF description
         * EObject from the given Sirius one.
         * 
         * @param description
         *            The Sirius description EObject
         * @return The converter found or <code>null</code> if none could be
         *         found
         */
        public IDescriptionConverter getDescriptionConverter(EObject description) {
            IDescriptionConverter converter = null;

            List<IItemDescriptor<IDescriptionConverter>> itemDescriptors = this.descriptionConverterRegistry.getItemDescriptors();
            for (IItemDescriptor<IDescriptionConverter> itemDescriptor : itemDescriptors) {
                IDescriptionConverter descriptionConverter = itemDescriptor.getItem();
                if (descriptionConverter.canHandle(description)) {
                    converter = descriptionConverter;
                    break;
                }
            }

            if (converter == null) {
                if (description instanceof SemanticValidationRule) {
                    converter = new SemanticValidationRuleDescriptionConverter();
                } else if (description instanceof RuleAudit) {
                    converter = new DefaultDescriptionConverter<>(RuleAudit.class, EefPackage.Literals.EEF_RULE_AUDIT_DESCRIPTION);
                } else if (description instanceof ValidationFix) {
                    converter = new DefaultDescriptionWithInitialOperationConverter<>(ValidationFix.class, EefPackage.Literals.EEF_VALIDATION_FIX_DESCRIPTION,
                            EefPackage.Literals.EEF_VALIDATION_FIX_DESCRIPTION__FIX_EXPRESSION);
                } else {
                    PropertiesDescriptionConverterSwitch descriptionConverterSwitch = new PropertiesDescriptionConverterSwitch();
                    converter = descriptionConverterSwitch.doSwitch(description);
                }
            }
            return converter;
        }

        /**
         * Returns the link resolvers used to update the converted description.
         * 
         * @return The link resolvers
         */
        public List<IDescriptionLinkResolver> getDescriptionLinkResolvers() {
            List<IDescriptionLinkResolver> linkResolvers = new ArrayList<>();
            linkResolvers.add(new PropertyValidationRuleLinkResolver());

            List<IItemDescriptor<IDescriptionLinkResolver>> itemDescriptors = this.descriptionLinkResolverRegistry.getItemDescriptors();
            for (IItemDescriptor<IDescriptionLinkResolver> itemDescriptor : itemDescriptors) {
                IDescriptionLinkResolver linkResolver = itemDescriptor.getItem();
                linkResolvers.add(linkResolver);
            }

            return linkResolvers;
        }

        /**
         * Starts the management of the Preferences of the core of Designer.
         *
         */
        private void startPropertiesPreferencesManagement() {
            propertyChangeListener = new IPropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent event) {
                    Object newValue = event.getNewValue();
                    preferenceStore.putValue(event.getProperty(), String.valueOf(newValue));
                }
            };
            getPreferenceStore().addPropertyChangeListener(propertyChangeListener);
        }

        /**
         * Stops the management of the Preferences of the core of Designer.
         *
         */
        private void stopPropertiesPreferencesManagement() {
            getPreferenceStore().removePropertyChangeListener(propertyChangeListener);
        }

        /**
         * Returns the preference store for this UI plug-in. This preference
         * store is used to hold persistent settings for this plug-in in the
         * context of a workbench. Some of these settings will be user
         * controlled.
         * <p>
         * If an error occurs reading the preference store, an empty preference
         * store is quietly created, initialized with defaults, and returned.
         * </p>
         *
         * @return the preference store
         */
        public IPreferenceStore getPreferenceStore() {
            // Create the preference store lazily.
            if (preferenceStore == null) {
                preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, INSTANCE.getSymbolicName());

            }
            return preferenceStore;
        }
    }
}
