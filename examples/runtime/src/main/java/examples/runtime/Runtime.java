/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package examples.runtime;

import examples.model.Author;
import examples.model.Book;
import examples.model.Language;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbContext;
import javax.json.bind.JsonbMarshaller;
import javax.json.bind.spi.JsonbProvider;

/**
 *
 * @author Martin Grebac
 */
public class Runtime {

    public static void main(String[] args) {

        Book book = new Book();
        book.id = 101L;
        book.lang = Language.CZECH;

        book.author = new Author();
        book.author.firstName = "Jara";
        book.author.lastName = "Cimrman";

// DEFAULT EASE OF USE METHODS

        /**
         * Write an object content tree using default JSON mapping
            {
              "id" : 101,
              "author" : {
                "firstName" : "Jara",
                "lastName" : "Cimrman"
              },
              "lang" : "CZECH"
            }
        */
        String json = Jsonb.marshal(book);

        /**
         * Read JSON document (from above) into an object content tree using default mapping
         */
        Book b1 = Jsonb.unmarshal(json, Book.class);


// CONTEXT CREATION

        /**
         * Create context using Jsonb class.
         */
        Jsonb.createContext(Book.class);

        /**
         * Create context using default Provider.
         */
        JsonbProvider.provider().createContext(Book.class);

        /**
         * Create context using specific Provider.
         */
        JsonbProvider.provider("org.eclipse.persistence.json.bind.JsonBindingProvider").createContext(Book.class);

        /**
         * Create context using Builder pattern, use it to create unmarshaller and unmarshal
         * a json string.
         */
        JsonbContext context = new JsonbContext.Builder()
                .setClasses(Book.class, Author.class, Language.class)
                .setProperty(JsonbMarshaller.JSON_BIND_FORMATTED_OUTPUT, true)
                .build();
        System.out.println(context);
        Book b2 = (Book) context.createUnmarshaller().unmarshal(json);

    }

}
