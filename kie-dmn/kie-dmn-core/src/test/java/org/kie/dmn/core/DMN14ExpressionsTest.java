/*
 * Copyright 2005 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.dmn.core;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.kie.dmn.api.core.DMNMessageType;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.impl.DMNContextImpl;
import org.kie.dmn.core.util.DMNRuntimeUtil;

import static org.junit.Assert.assertEquals;

public class DMN14ExpressionsTest extends BaseInterpretedVsCompiledTest {

    private DMNRuntime runtime;
    private DMNModel model;

    public DMN14ExpressionsTest(final boolean useExecModelCompiler) {
        super(useExecModelCompiler);
    }
    
    @Before
    public void setup() {
        runtime = DMNRuntimeUtil.createRuntime("dmn14expressions.dmn", this.getClass());
        model = runtime.getModel("http://www.trisotech.com/definitions/_3404349f-5046-4ad3-ad15-7f1e27291ab5", "DMN 1.4 expressions");        
    }
    
    @Test
    public void testConditionalWithInput() throws Throwable {
        DMNResult results = runtime.evaluateByName(model, new DMNContextImpl(Collections.singletonMap("Boolean Input", true)), "Conditional");
        assertEquals("Conditional evaluated to TRUE", results.getDecisionResultByName("Conditional").getResult());

         results = runtime.evaluateByName(model, new DMNContextImpl(Collections.singletonMap("Boolean Input", false)), "Conditional");
        assertEquals("Conditional evaluated to FALSE", results.getDecisionResultByName("Conditional").getResult());

         results = runtime.evaluateByName(model, new DMNContextImpl(Collections.singletonMap("Boolean Input", null)), "Conditional");
        assertEquals("Conditional evaluated to FALSE", results.getDecisionResultByName("Conditional").getResult());

    }

    @Test
    public void testConditionalNonBooleanIf() throws Throwable {
        final DMNRuntime runtime = DMNRuntimeUtil.createRuntime("dmn14expressions.dmn", this.getClass());
        DMNModel model = runtime.getModel("http://www.trisotech.com/definitions/_3404349f-5046-4ad3-ad15-7f1e27291ab5", "DMN 1.4 expressions");
        DMNResult results = runtime.evaluateByName(model, new DMNContextImpl(), "Non boolean");
        assertEquals(1, results.getMessages().size());
        assertEquals(DMNMessageType.ERROR_EVAL_NODE, results.getMessages().iterator().next().getMessageType());

    }

//    @Test
//    public void testConditionalErrorInIf() throws Throwable {
//        final DMNRuntime runtime = DMNRuntimeUtil.createRuntime("dmn14expressions.dmn", this.getClass());
//        DMNModel model = runtime.getModel("http://www.trisotech.com/definitions/_3404349f-5046-4ad3-ad15-7f1e27291ab5", "DMN 1.4 expressions");
//        DMNResult results = runtime.evaluateByName(model, new DMNContextImpl(), "Invalid variable");
//        assertEquals(1, results.getMessages().size());
//        assertEquals(DMNMessageType.ERROR_EVAL_NODE, results.getMessages().iterator().next().getMessageType());
//
//    }

    
    
    
}
