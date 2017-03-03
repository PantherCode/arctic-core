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
package org.panthercode.arctic.core.processing.modules;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * If a .jar file contains more than one module class for class loader it's not possible to detect the root element.
 * Therefore it's always necessary to annotate the root element to indicate the right one. Every .jar file must contains
 * one and only one root element, also if the library contains only one module. Otherwise class loader will react with
 * an error, that the library doesn't contain any executable code and reject the .jar file.
 *
 * @author PantherCode
 * @see Module
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RootModule {
}
