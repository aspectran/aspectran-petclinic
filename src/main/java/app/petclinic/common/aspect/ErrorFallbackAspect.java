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
package app.petclinic.common.aspect;

import com.aspectran.core.activity.Translet;
import com.aspectran.core.component.bean.annotation.Aspect;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.component.bean.annotation.Dispatch;
import com.aspectran.core.component.bean.annotation.ExceptionThrown;
import com.aspectran.core.component.bean.annotation.Joinpoint;
import com.aspectran.utils.annotation.jsr305.NonNull;
import com.aspectran.web.support.http.HttpStatusSetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Bean
@Aspect("errorFallbackAspect")
public class ErrorFallbackAspect {

    private static final Logger logger = LoggerFactory.getLogger(ErrorFallbackAspect.class);

    @ExceptionThrown
    @Dispatch("error")
    public void fallback(@NonNull Translet translet) {
        logger.error("Raised exception", translet.getRaisedException());
        translet.setAttribute("message", translet.getRaisedException().getMessage());
    }

}
