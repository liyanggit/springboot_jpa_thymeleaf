package com.lyng.study.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/14.
 * 书单类数据接口层
 * extends JpaRepository<Book,Long> 可以实现一些基本的增删改查和基于命名规则的增删改查
 * @Repository 注解标注此类纳入spring管理,专指数据接口层,可替换为通用的@Component
 */
@Repository
public interface BookRepository extends JpaRepository<Book,Long>{

    /**
     * jpa-基于命名规则的分页查询
     * @param pageable
     * @return
     */
    @Override
    Page<Book> findAll(Pageable pageable);

    /**
     * 基于jpa命名规则的复杂查询
     * findByFirstname,findByFirstnameIs,findByFirstnameEquals
     * where x.firstname = ?1
     * By XXX某个字段
     */
    List<Book> findByAuthor(String author);

    /**
     * 基于jpa命名规则的复杂查询-And
     * findByLastnameAndFirstname
     * where x.lastname = ?1 and x.firstname = ?2
     * @param author
     * @param status
     * @return
     */
    List<Book> findByAuthorAndStatus(String author,int status);

    /**
     * 基于jpa命名规则的复杂查询-Or
     * findByLastnameOrFirstname
     * where x.lastname = ?1 or x.firstname = ?2
     * @param author
     * @param status
     * @return
     */
    List<Book> findByAuthorOrStatus(String author,int status);

    /**
     * 基于jpa命名规则的复杂查询-Between
     * findByStartDateBetween
     * where x.startDate between ?1 and ?2
     * @param start
     * @param end
     * @return
     */
    List<Book> findByStatusBetween(int start,int end);

    /**
     * 基于jpa命名规则的复杂查询-LessThan
     * findByAgeLessThan
     * where x.age < ?1
     * @param status
     * @return
     */
    List<Book> findByStatusLessThan(int status);

    /**
     * 基于jpa命名规则的复杂查询-LessThanEqual
     * findByAgeLessThanEqual
     * where x.age <= ?1
     * @param status
     * @return
     */
    List<Book> findByStatusLessThanEqual(int status);

    /**
     * 基于jpa命名规则的复杂查询-GreaterThan
     * findByAgeGreaterThan
     * where x.age > ?1
     * @param status
     * @return
     */
    List<Book> findByStatusGreaterThan(int status);

    /**
     * 基于jpa命名规则的复杂查询-GreaterThanEqual
     * findByAgeGreaterThanEqual
     * where x.age >= ?1
     * @param status
     * @return
     */
    List<Book> findByStatusGreaterThanEqual(int status);

    /**
     * 基于jpa命名规则的复杂查询-After
     * findByStartDateAfter
     * where x.startDate > ?1
     * @param status
     * @return
     */
    List<Book> findByStatusAfter(int status);

    /**
     * 基于jpa命名规则的复杂查询-Before
     * findByStartDateBefore
     * where x.startDate < ?1
     * @param status
     * @return
     */
    List<Book> findByStatusBefore(int status);

    /**
     * 基于jpa命名规则的复杂查询-IsNull
     * findByAgeIsNull
     * where x.age is null
     * @return
     */
    List<Book> findByAuthorIsNull();

    /**
     * 基于jpa命名规则的复杂查询-IsNotNull,NotNull
     * findByAge(Is)NotNull
     * where x.age not null
     * @return
     */
    List<Book> findByAuthorIsNotNull();

    /**
     * 基于jpa命名规则的复杂查询-Like
     * findByFirstnameLike
     * where x.firstname like ?1
     * @param desc
     * @return
     */
    List<Book> findByDescriptionLike(String desc);

    /**
     * 基于jpa命名规则的复杂查询-NotLike
     * findByFirstnameNotLike
     * where x.firstname not like ?1
     * @param desc
     * @return
     */
    List<Book> findByDescriptionNotLike(String desc);

    /**
     * 基于jpa命名规则的复杂查询-StartingWith
     * findByFirstnameStartingWith
     * where x.firstname like ?1
     * parameter bound with appended %
     * @param name
     * @return
     */
    List<Book> findByNameStartingWith(String name);

    /**
     * 基于jpa命名规则的复杂查询-EndingWith
     * findByFirstnameEndingWith
     * where x.firstname like ?1
     * parameter bound with prepended %
     * @param name
     * @return
     */
    List<Book> findByNameEndingWith(String name);

    /**
     * 基于jpa命名规则的复杂查询-Containing
     * findByFirstnameContaining
     * where x.firstname like ?1
     * parameter bound wrapped in %
     * @param name
     * @return
     */
    List<Book> findByNameContaining(String name);

    /**
     * 基于jpa命名规则的复杂查询-OrderBy
     * findByAgeOrderByLastnameDesc
     * where x.age = ?1 order by x.lastname desc
     * @param name
     * @return
     */
    List<Book> findByNameOrderByIdDesc(String name);

    /**
     * 基于jpa命名规则的复杂查询-Not
     * findByLastnameNot
     * where x.lastname <> ?1
     * @param status
     * @return
     */
    List<Book> findByStatusNot(int status);

    /**
     * 基于jpa命名规则的复杂查询-In
     * findByAgeIn(Collection<Age> ages)
     * where x.age in ?1
     * @param status
     * @return
     */
    List<Book> findByStatusIn(Collection<Integer> status);

    /**
     * 基于jpa命名规则的复杂查询-NotIn
     * findByAgeNotIn(Collection<Age> ages)
     * where x.age not in ?1
     * @param status
     * @return
     */
    List<Book> findByStatusNotIn(Collection<Integer> status);

    /**
     * 基于jpa命名规则的复杂查询-True
     * findByActiveTrue()
     * where x.active = true
     * @return
     */
    List<Book> findByStatusTrue();

    /**
     * 基于jpa命名规则的复杂查询-False
     * findByActiveFalse()
     * where x.active = false
     * @return
     */
    List<Book> findByStatusFalse();

    /**
     * 自定义查询
     * JPQL语句 类似于HQL语句
     * 如果返回自定义字段,用map接,之后在service层转换成实体
     * 如果返回自定义的字段太多,建议新建实体接收
     * @param status
     * @return
     */
    @Query(value = "select new map(b.id,b.author) from Book b where b.status <> ?1",nativeQuery = false)
    List<Map> findByCustom(int status);

    /**
     * 自定义查询
     * 如果返回全表字段,直接用对象接
     * @param nameLen
     * @return
     */
    @Query(value = "select * from book where length(name) > ?1 ",nativeQuery = true)
    List<Book> findEntityByCustome(int nameLen);

    /**
     * 自定义更新
     * 包括insert/update/delete
     * 这里除了有自定义的sql语句外,还需要加@Modifying注解
     * 因为DML语句,为了保证数据的完整性,需要加@Transactional控制事务
     * 但一般上@Transactional注解应加在service层
     * nativeQuery---为true,代表执行的是sql语句,否则,执行的是jpql语句,默认为false
     */
    @Transactional
    @Modifying
    @Query(value = "update book set name=?1 where id = ?2",nativeQuery = true)
    int editBookHandler(String name,long id);

    @Transactional
    @Modifying
    @Query(value = "delete from book where id = ?1",nativeQuery = true)
    int removeBookHandler(long id);

}
