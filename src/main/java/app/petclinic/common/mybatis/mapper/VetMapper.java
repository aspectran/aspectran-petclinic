/*
 * Copyright 2010-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.petclinic.common.mybatis.mapper;

import app.petclinic.common.pagination.PageInfo;
import app.petclinic.vet.Vet;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.mybatis.SqlMapperAccess;
import com.aspectran.mybatis.SqlMapperProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * The Interface AccountMapper.
 *
 * @author Juho Jeong
 */
@Mapper
public interface VetMapper {

    int getVetTotal();

    List<Vet> getVetList(@Param("page") PageInfo pageInfo);

    @Component
    class Dao extends SqlMapperAccess<VetMapper> implements VetMapper {

        @Autowired
        public Dao(SqlMapperProvider sqlMapperProvider) {
            super(sqlMapperProvider, VetMapper.class);
        }

        @Override
        public int getVetTotal() {
            return simple().getVetTotal();
        }

        @Override
        public List<Vet> getVetList(PageInfo pageInfo) {
            return simple().getVetList(pageInfo);
        }

    }

}
