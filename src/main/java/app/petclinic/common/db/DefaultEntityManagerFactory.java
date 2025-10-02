/*
 * Copyright (c) 2012-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.petclinic.common.db;

import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.component.bean.annotation.Initialize;
import com.aspectran.jpa.EntityManagerFactoryBean;
import jakarta.persistence.PersistenceConfiguration;
import jakarta.persistence.PersistenceUnitTransactionType;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.tool.schema.Action;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Default EntityManagerFactory configuration for the PetClinic application.
 *
 * <p>This factory bean creates and configures a JPA {@link jakarta.persistence.EntityManagerFactory}
 * using Hibernate as the persistence provider. It is responsible for:
 * <ul>
 *     <li>Configuring the persistence unit with the provided DataSource</li>
 *     <li>Setting up Hibernate-specific properties including ClassLoader configuration</li>
 *     <li>Managing schema generation behavior based on the active profile</li>
 * </ul>
 *
 * <p><b>Profile-based Configuration:</b><br>
 * The factory supports different configurations based on Aspectran profiles:
 * <ul>
 *     <li><b>Development Mode</b> ({@code !prod}): Schema generation disabled</li>
 *     <li><b>Production Mode</b> ({@code prod}): Schema generation disabled</li>
 * </ul>
 *
 * @see EntityManagerFactoryBean
 * @see HibernatePersistenceProvider
 *
 * <p>Created: 2025-05-02</p>
 */
@Component
@Bean(lazyDestroy = true)
public class DefaultEntityManagerFactory extends EntityManagerFactoryBean {

    private final DataSource dataSource;

    /**
     * Constructs a new DefaultEntityManagerFactory with the specified DataSource.
     * @param dataSource the DataSource to be used for database connections
     */
    @Autowired
    public DefaultEntityManagerFactory(DataSource dataSource) {
        super("petclinic");
        this.dataSource = dataSource;
    }

    @Override
    protected void preConfigure(Map<String, Object> properties) {
        super.preConfigure(properties);
    }

    /**
     * Pre-configures the PersistenceConfiguration before EntityManagerFactory creation.
     * <p>This method sets up:
     * <ul>
     *     <li>Hibernate as the persistence provider</li>
     *     <li>RESOURCE_LOCAL transaction management</li>
     *     <li>Non-JTA DataSource configuration</li>
     * </ul>
     * @param configuration the PersistenceConfiguration to be customized
     */
    @Override
    protected void preConfigure(PersistenceConfiguration configuration) {
        super.preConfigure(configuration);
        configuration.provider(HibernatePersistenceProvider.class.getName());
        configuration.transactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL);
        configuration.property(JdbcSettings.JAKARTA_NON_JTA_DATASOURCE, dataSource);
    }

    /**
     * Initializes the EntityManagerFactory for development mode.
     * <p>This method is invoked when the application is running in any profile
     * except production.
     */
    @Initialize(profile = "!prod")
    public void initInDevMode() {
        setProperty(AvailableSettings.HBM2DDL_AUTO, Action.ACTION_UPDATE);
    }

    /**
     * Initializes the EntityManagerFactory for production mode.
     * <p>This method is invoked when the application is running in production profile.
     */
    @Initialize(profile = "prod")
    public void initInProdMode() {
        setProperty(AvailableSettings.HBM2DDL_AUTO, Action.ACTION_CREATE_ONLY);
    }

}
