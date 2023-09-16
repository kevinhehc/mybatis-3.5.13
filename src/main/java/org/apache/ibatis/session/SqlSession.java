/*
 *    Copyright 2009-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.session;

import java.io.Closeable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;

/**
 * The primary Java interface for working with MyBatis. Through this interface you can execute commands, get mappers and
 * manage transactions.
 *
 * @author Clinton Begin
 */
// 这是MyBatis主要的一个类，用来执行SQL，获取映射器，管理事务
// 通常情况下，我们在应用程序中使用的Mybatis的API就是这个接口定义的方法。
public interface SqlSession extends Closeable {

  /**
   * Retrieve a single row mapped from the statement key.
   *
   * @param <T>
   *          the returned object type
   * @param statement
   *          the statement
   *
   * @return Mapped object
   */
  <T> T selectOne(String statement);

  /**
   * Retrieve a single row mapped from the statement key and parameter.
   *
   * @param <T>
   *          the returned object type
   * @param statement
   *          Unique identifier matching the statement to use.
   * @param parameter
   *          A parameter object to pass to the statement.
   *
   * @return Mapped object
   */
  <T> T selectOne(String statement, Object parameter);

  /**
   * Retrieve a list of mapped objects from the statement key.
   *
   * @param <E>
   *          the returned list element type
   * @param statement
   *          Unique identifier matching the statement to use.
   *
   * @return List of mapped object
   */
  <E> List<E> selectList(String statement);

  /**
   * Retrieve a list of mapped objects from the statement key and parameter.
   *
   * @param <E>
   *          the returned list element type
   * @param statement
   *          Unique identifier matching the statement to use.
   * @param parameter
   *          A parameter object to pass to the statement.
   *
   * @return List of mapped object
   */
  <E> List<E> selectList(String statement, Object parameter);

  /**
   * Retrieve a list of mapped objects from the statement key and parameter, within the specified row bounds.
   *
   * @param <E>
   *          the returned list element type
   * @param statement
   *          Unique identifier matching the statement to use.
   * @param parameter
   *          A parameter object to pass to the statement.
   * @param rowBounds
   *          Bounds to limit object retrieval
   *
   * @return List of mapped object
   */
  <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds);

  /**
   * The selectMap is a special case in that it is designed to convert a list of results into a Map based on one of the
   * properties in the resulting objects. Eg. Return a of Map[Integer,Author] for selectMap("selectAuthors","id")
   *
   * @param <K>
   *          the returned Map keys type
   * @param <V>
   *          the returned Map values type
   * @param statement
   *          Unique identifier matching the statement to use.
   * @param mapKey
   *          The property to use as key for each value in the list.
   *
   * @return Map containing key pair data.
   */
  // 获取多条记录,加上分页,并存入Map
  <K, V> Map<K, V> selectMap(String statement, String mapKey);

  /**
   * The selectMap is a special case in that it is designed to convert a list of results into a Map based on one of the
   * properties in the resulting objects.
   *
   * @param <K>
   *          the returned Map keys type
   * @param <V>
   *          the returned Map values type
   * @param statement
   *          Unique identifier matching the statement to use.
   * @param parameter
   *          A parameter object to pass to the statement.
   * @param mapKey
   *          The property to use as key for each value in the list.
   *
   * @return Map containing key pair data.
   */
  // 获取多条记录,加上分页,并存入Map
  <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey);

  /**
   * The selectMap is a special case in that it is designed to convert a list of results into a Map based on one of the
   * properties in the resulting objects.
   *
   * @param <K>
   *          the returned Map keys type
   * @param <V>
   *          the returned Map values type
   * @param statement
   *          Unique identifier matching the statement to use.
   * @param parameter
   *          A parameter object to pass to the statement.
   * @param mapKey
   *          The property to use as key for each value in the list.
   * @param rowBounds
   *          Bounds to limit object retrieval
   *
   * @return Map containing key pair data.
   */
  // 获取多条记录,加上分页,并存入Map
  <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds);

  /**
   * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.
   *
   * @param <T>
   *          the returned cursor element type.
   * @param statement
   *          Unique identifier matching the statement to use.
   *
   * @return Cursor of mapped objects
   */
  <T> Cursor<T> selectCursor(String statement);

  /**
   * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.
   *
   * @param <T>
   *          the returned cursor element type.
   * @param statement
   *          Unique identifier matching the statement to use.
   * @param parameter
   *          A parameter object to pass to the statement.
   *
   * @return Cursor of mapped objects
   */
  <T> Cursor<T> selectCursor(String statement, Object parameter);

  /**
   * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.
   *
   * @param <T>
   *          the returned cursor element type.
   * @param statement
   *          Unique identifier matching the statement to use.
   * @param parameter
   *          A parameter object to pass to the statement.
   * @param rowBounds
   *          Bounds to limit object retrieval
   *
   * @return Cursor of mapped objects
   */
  <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds);

  /**
   * Retrieve a single row mapped from the statement key and parameter using a {@code ResultHandler}.
   *
   * @param statement
   *          Unique identifier matching the statement to use.
   * @param parameter
   *          A parameter object to pass to the statement.
   * @param handler
   *          ResultHandler that will handle each retrieved row
   */
  void select(String statement, Object parameter, ResultHandler handler);

  /**
   * Retrieve a single row mapped from the statement using a {@code ResultHandler}.
   *
   * @param statement
   *          Unique identifier matching the statement to use.
   * @param handler
   *          ResultHandler that will handle each retrieved row
   */
  // 获取一条记录,并转交给ResultHandler处理。这个方法容许我们自己定义对
  // 查询到的行的处理方式。不过一般用的并不是很多
  void select(String statement, ResultHandler handler);

  /**
   * Retrieve a single row mapped from the statement key and parameter using a {@code ResultHandler} and
   * {@code RowBounds}.
   *
   * @param statement
   *          Unique identifier matching the statement to use.
   * @param parameter
   *          the parameter
   * @param rowBounds
   *          RowBound instance to limit the query results
   * @param handler
   *          ResultHandler that will handle each retrieved row
   */
  // 获取一条记录,加上分页,并转交给ResultHandler处理
  void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler);

  /**
   * Execute an insert statement.
   *
   * @param statement
   *          Unique identifier matching the statement to execute.
   *
   * @return int The number of rows affected by the insert.
   */
  // 插入记录，容许传入参数。
  int insert(String statement);

  /**
   * Execute an insert statement with the given parameter object. Any generated autoincrement values or selectKey
   * entries will modify the given parameter object properties. Only the number of rows affected will be returned.
   *
   * @param statement
   *          Unique identifier matching the statement to execute.
   * @param parameter
   *          A parameter object to pass to the statement.
   *
   * @return int The number of rows affected by the insert.
   */
  // 更新记录。返回的是受影响的行数
  int insert(String statement, Object parameter);

  /**
   * Execute an update statement. The number of rows affected will be returned.
   *
   * @param statement
   *          Unique identifier matching the statement to execute.
   *
   * @return int The number of rows affected by the update.
   */
  int update(String statement);

  /**
   * Execute an update statement. The number of rows affected will be returned.
   *
   * @param statement
   *          Unique identifier matching the statement to execute.
   * @param parameter
   *          A parameter object to pass to the statement.
   *
   * @return int The number of rows affected by the update.
   */
  // 更新记录
  int update(String statement, Object parameter);

  /**
   * Execute a delete statement. The number of rows affected will be returned.
   *
   * @param statement
   *          Unique identifier matching the statement to execute.
   *
   * @return int The number of rows affected by the delete.
   */
  // 删除记录
  int delete(String statement);

  /**
   * Execute a delete statement. The number of rows affected will be returned.
   *
   * @param statement
   *          Unique identifier matching the statement to execute.
   * @param parameter
   *          A parameter object to pass to the statement.
   *
   * @return int The number of rows affected by the delete.
   */
  int delete(String statement, Object parameter);

  // 以下是事务控制方法,commit,rollback
  /**
   * Flushes batch statements and commits database connection. Note that database connection will not be committed if no
   * updates/deletes/inserts were called. To force the commit call {@link SqlSession#commit(boolean)}
   */
  void commit();

  /**
   * Flushes batch statements and commits database connection.
   *
   * @param force
   *          forces connection commit
   */
  void commit(boolean force);

  /**
   * Discards pending batch statements and rolls database connection back. Note that database connection will not be
   * rolled back if no updates/deletes/inserts were called. To force the rollback call
   * {@link SqlSession#rollback(boolean)}
   */
  void rollback();

  /**
   * Discards pending batch statements and rolls database connection back. Note that database connection will not be
   * rolled back if no updates/deletes/inserts were called.
   *
   * @param force
   *          forces connection rollback
   */
  void rollback(boolean force);

  /**
   * Flushes batch statements.
   *
   * @return BatchResult list of updated records
   *
   * @since 3.0.6
   */
  // 刷新批处理语句,返回批处理结果
  List<BatchResult> flushStatements();

  /**
   * Closes the session.
   */
  // 关闭Session
  @Override
  void close();

  /**
   * Clears local session cache.
   */
  // 清理Session缓存
  void clearCache();

  /**
   * Retrieves current configuration.
   *
   * @return Configuration
   */
  // 得到配置
  Configuration getConfiguration();

  /**
   * Retrieves a mapper.
   *
   * @param <T>
   *          the mapper type
   * @param type
   *          Mapper interface class
   *
   * @return a mapper bound to this SqlSession
   */
  // 得到映射器
  // 这个巧妙的使用了泛型，使得类型安全
  // 到了MyBatis 3，还可以用注解,这样xml都不用写了
  <T> T getMapper(Class<T> type);

  /**
   * Retrieves inner database connection.
   *
   * @return Connection
   */
  // 得到数据库连接
  Connection getConnection();
}
