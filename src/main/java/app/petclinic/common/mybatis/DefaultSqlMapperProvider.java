/*
 * Copyright (c) 2008-2025 The Aspectran Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.petclinic.common.mybatis;

import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.mybatis.SqlMapperProvider;
import org.apache.ibatis.session.SqlSession;

@Component
@Bean(lazyDestroy = true)
public final class DefaultSqlMapperProvider implements SqlMapperProvider {

    private final SqlSession simpleSqlSession;

    private final SqlSession batchSqlSession;

    private final SqlSession reuseSqlSession;

    @Autowired
    public DefaultSqlMapperProvider(SimpleSqlSession simpleSqlSession,
                                    BatchSqlSession batchSqlSession,
                                    ReuseSqlSession reuseSqlSession) {
        this.simpleSqlSession = simpleSqlSession;
        this.batchSqlSession = batchSqlSession;
        this.reuseSqlSession = reuseSqlSession;
    }

    @Override
    public SqlSession getSimpleSqlSession() {
        return simpleSqlSession;
    }

    @Override
    public SqlSession getBatchSqlSession() {
        return batchSqlSession;
    }

    @Override
    public SqlSession getReuseSqlSession() {
        return reuseSqlSession;
    }

}
