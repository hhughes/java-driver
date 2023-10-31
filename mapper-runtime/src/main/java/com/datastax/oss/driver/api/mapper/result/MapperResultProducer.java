/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datastax.oss.driver.api.mapper.result;

import com.datastax.oss.driver.api.core.cql.Statement;
import com.datastax.oss.driver.api.core.data.GettableByName;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import java.util.concurrent.CompletionStage;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A component that can be plugged into the object mapper, in order to return custom result types
 * from DAO methods.
 *
 * <p>For example, this could be used to substitute a 3rd-party future implementation for {@link
 * CompletionStage}:
 *
 * <pre>
 * public class CustomFutureProducer implements MapperResultProducer {
 *   ...
 * }
 * </pre>
 *
 * <p>Producers are registered via the Java Service Provider mechanism (see {@link
 * MapperResultProducerService}). DAO methods can then use the new type:
 *
 * <pre>
 * &#64;Dao
 * public interface ProductDao {
 *   &#64;Select
 *   CustomFuture&lt;Product&gt; findById(UUID productId);
 * }
 * </pre>
 *
 * See the javadocs of the methods in this interface for more explanations.
 */
public interface MapperResultProducer {

  /**
   * Checks if this producer can handle a particular result type.
   *
   * <p>This will be invoked at runtime to select a producer: if a DAO method declares a return type
   * that is not supported natively, then the mapper generates an implementation which, for every
   * invocation, iterates through all the producers <em>in the order that they were registered</em>,
   * and picks the first one where {@code canProduce()} returns true.
   *
   * @param resultType the DAO method's declared return type. If checking the top-level type is
   *     sufficient, then {@link GenericType#getRawType()} should do the trick. If you need to
   *     recurse into the type arguments, call {@link GenericType#getType()} and use the {@code
   *     java.lang.reflect} APIs.
   */
  boolean canProduce(@Nonnull GenericType<?> resultType);

  /**
   * Executes the statement generated by the mapper, and converts the result to the expected type.
   *
   * <p>This will be executed at runtime, every time the DAO method is called.
   *
   * @param statement the statement, ready to execute: the mapper has already bound all the values,
   *     and set all the necessary attributes (consistency, page size, etc).
   * @param context the context in which the DAO method is executed. In particular, this is how you
   *     get access to the {@linkplain MapperContext#getSession() session}.
   * @param entityHelper if the type to produce contains a mapped entity (e.g. {@code
   *     ListenableFuture<Product>}), an instance of the helper class to manipulate that entity. In
   *     particular, {@link EntityHelper#get(GettableByName) entityHelper.get()} allows you to
   *     convert rows into entity instances. If the type to produce does not contain an entity, this
   *     will be {@code null}.
   * @return the object to return from the DAO method. This must match the type that this producer
   *     was selected for, there will be an unchecked cast at runtime.
   */
  @Nullable
  Object execute(
      @Nonnull Statement<?> statement,
      @Nonnull MapperContext context,
      @Nullable EntityHelper<?> entityHelper);

  /**
   * Surfaces any error encountered in the DAO method (either in the generated mapper code that
   * builds the statement, or during invocation of {@link #execute}).
   *
   * <p>For some result types, it is expected that errors will be wrapped in some sort of container
   * instead of thrown directly; for example a failed future or publisher.
   *
   * <p>If rethrowing is the right thing to do, then it is perfectly fine to do so from this method.
   * If you throw checked exceptions, they will be propagated directly if the DAO method also
   * declares them, or wrapped into a {@link RuntimeException} otherwise.
   */
  @Nullable
  Object wrapError(@Nonnull Exception e) throws Exception;
}
