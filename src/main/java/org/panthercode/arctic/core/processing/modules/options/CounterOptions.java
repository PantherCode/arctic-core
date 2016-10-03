/*
 * Copyright 2016 PantherCode
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.panthercode.arctic.core.processing.modules.options;

import org.panthercode.arctic.core.arguments.ArgumentUtils;

/**
 * TODO: documentation
 * TODO: Exceptions in function signature
 */
public class CounterOptions extends LoopOptions {

    /**
     *
     */
    private int count = 1;

    /**
     *
     */
    public CounterOptions(){
        super();
    }

    /**
     *
     * @param count
     */
    public CounterOptions(int count){
        this(1, 1000L);
    }

    /**
     *
     * @param count
     * @param delayTimeInMillis
     */
    public CounterOptions(int count, long delayTimeInMillis){
        this(1, delayTimeInMillis, true, true);
    }

    /**
     *
     * @param count
     * @param delayTimeInMillis
     * @param ignoreExceptions
     * @param canQuit
     */
    public CounterOptions(int count, long delayTimeInMillis, boolean ignoreExceptions, boolean canQuit){
        super(delayTimeInMillis, ignoreExceptions, canQuit);

        this.setCount(count);
    }

    /**
     *
     * @return
     */
    public int getCount(){
        return this.count;
    }

    /**
     *
     * @param count
     */
    public void setCount(int count){
        ArgumentUtils.assertGreaterOrEqualsZero(count, "count");

        this.count = count;
    }

}
