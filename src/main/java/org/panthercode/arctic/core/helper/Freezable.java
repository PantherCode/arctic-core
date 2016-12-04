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
package org.panthercode.arctic.core.helper;

/**
 * This interface provides the possibility to freeze the inner state of an object. This way a class can prevent
 * modifications by calling functions like setter itself.
 * <p>
 * It's not designed for usage in a security context to prevent damage by malicious behavior, but to prevent
 * inadvertent changes by calling setter or other modifiable functions accidentally or willingly.
 * <p>
 * To block all modifications you can activate the lock mechanism in freeze() function or by constructor and forbid to
 * unfreeze the object by implementing an empty function body, better throwing a UnsupportedOperationException.
 *
 * @author PantherCode
 */
public interface Freezable {

    /**
     * Freeze the inner state of an object and it's not possible to change the value of a class member field.
     */
    void freeze();

    /**
     * Unfreeze the inner state of an object and it's possible to change the value of a class member field.
     */
    void unfreeze();

    /**
     * Returns a flag representing the inner state of the object can be modified or not.
     *
     * @return Returns <tt>true</tt> if the object can be modified; Otherwise <tt>false</tt>.
     */
    boolean canModify();
}
