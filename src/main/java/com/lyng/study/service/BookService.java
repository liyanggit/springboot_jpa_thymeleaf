package com.lyng.study.service;

import com.lyng.study.domain.Book;
import com.lyng.study.domain.BookRepository;
import com.lyng.study.domain.BookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/14.
 * 书单类业务逻辑层
 * @Service 注解标注此类纳入spring管理,专指业务逻辑层,可替换为通用的@Component
 */
@Service
public class BookService {

    /**
     * 自动注入数据层接口
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * RESTful api-保存书单-业务逻辑层
     * @param book
     * @return
     * 注意:jpa-save方法的返回值是保存/更新之后的这个对象
     */
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    /**
     * RESTful api-查找全部书单
     * @return
     */
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    /**
     * RESTful api-查看书单详情
     * @param id
     * @return
     */
    public Book findOne(long id){
        return bookRepository.findOne(id);
    }

    /**
     * RESTful api-移除书单
     * @param id
     */
    public void removeBook(long id){
        bookRepository.delete(id);
    }

    /**
     * 分页查询
     */
    public Page<Book> getBooksByPage(Pageable pageable){
        /**
         * 参数:
         * page:页码
         * size:大小
         * sort(可选,排序用)
         *      参数:1-排序规则
         *          2-排序字段
         */
        //Sort sort = new Sort(Sort.Direction.DESC,"id");
        //Pageable pageable = new PageRequest(1,5,sort);
        return bookRepository.findAll(pageable);
    }

    /**
     * 基于某个字段的查询
     * @return
     */
    public List<Book> findByAuthor(String author){
        return bookRepository.findByAuthor(author);
    }

    /**
     * 基于And的查询
     */
    public List<Book> findByAuthorAndStatus(String author,int status){
        return bookRepository.findByAuthorAndStatus(author,status);
    }

    /**
     * 基于Or的查询
     */
    public List<Book> findByAuthorOrStatus(String author,int status){
        return bookRepository.findByAuthorOrStatus(author,status);
    }

    /**
     * 基于Between的查询
     */
    public List<Book> findByStatusBetween(int start,int end){
        return bookRepository.findByStatusBetween(start,end);
    }

    /**
     * 基于LessThan的查询
     */
    public List<Book> findByStatusLessThan(int status){
        return bookRepository.findByStatusLessThan(status);
    }

    /**
     * 基于LessThanEqual的查询
     */
    public List<Book> findByStatusLessThanEqual(int status){
        return bookRepository.findByStatusLessThanEqual(status);
    }

    /**
     * 基于GreaterThanEqual的查询
     */
    public List<Book> findByStatusGreaterThanEqual(int status){
        return bookRepository.findByStatusGreaterThanEqual(status);
    }

    /**
     * 基于GreaterThan的查询
     */
    public List<Book> findByStatusGreaterThan(int status){
        return bookRepository.findByStatusGreaterThan(status);
    }

    /**
     * 基于After的查询
     */
    public List<Book> findByStatusAfter(int status){
        return bookRepository.findByStatusAfter(status);
    }

    /**
     * 基于Before的查询
     */
    public List<Book> findByStatusBefore(int status){
        return bookRepository.findByStatusBefore(status);
    }

    /**
     * 基于IsNull的查询
     */
    public List<Book> findByAuthorIsNull(){
        return bookRepository.findByAuthorIsNull();
    }

    /**
     * 基于IsNotNull的查询
     */
    public List<Book> findByAuthorIsNotNull(){
        return bookRepository.findByAuthorIsNotNull();
    }

    /**
     * 基于Like的查询
     */
    public List<Book> findByDescriptionLike(String desc){
        return bookRepository.findByDescriptionLike(desc);
    }

    /**
     * 基于NotLike的查询
     */
    public List<Book> findByDescriptionNotLike(String desc){
        return bookRepository.findByDescriptionNotLike(desc);
    }

    /**
     * 基于StartingWith的查询
     */
    public List<Book> findByNameStartingWith(String name){
        return bookRepository.findByNameStartingWith(name);
    }

    /**
     * 基于EndingWith的查询
     */
    public List<Book> findByNameEndingWith(String name){
        return bookRepository.findByNameEndingWith(name);
    }

    /**
     * 基于Containing的查询
     */
    public List<Book> findByNameContaining(String name){
        return bookRepository.findByNameContaining(name);
    }

    /**
     * 基于OrderBy的查询
     */
    public List<Book> findByNameOrderByIdDesc(String name){
        return bookRepository.findByNameOrderByIdDesc(name);
    }

    /**
     * 基于Not的查询
     */
    public List<Book> findByStatusNot(int status){
        return bookRepository.findByStatusNot(status);
    }

    /**
     * 基于In的查询
     */
    public List<Book> findByStatusIn(Collection<Integer> status){
        return bookRepository.findByStatusIn(status);
    }

    /**
     * 基于NotIn的查询
     */
    public List<Book> findByStatusNotIn(Collection<Integer> status){
        return bookRepository.findByStatusNotIn(status);
    }

    /**
     * 基于自定义查询
     *
     */
    public List<BookVo> findByCustom(int status){
        List<BookVo> list = new ArrayList<>();
        List<Map> byCustom = bookRepository.findByCustom(status);
        if(byCustom != null && byCustom.size() != 0){
            for (Map map:byCustom) {
                BookVo book = new BookVo((Long) map.get("0"),(String)map.get("1"));
                list.add(book);
            }
        }
        return list;
    }

    /**
     * 基于自定义查询
     *
     */
    public List<Book> findEntityByCustome(int nameLen){
        return bookRepository.findEntityByCustome(nameLen);
    }

    /**
     * 自定义更新
     * 注意控制事务
     * readOnly ---默认为false,表示以下方法中有insert/update/delete等操作,需要控制事务
     * 如果为true,表示以下方法中都是查询sql,不需要事务管理
     * rollbackFor ---对于抛出哪些异常需要回滚
     * @param name
     * @param id
     * @return
     */
    @Transactional(readOnly = false,rollbackFor = RuntimeException.class)
    public int editBookHandler(String name,long id){
        try {
            /**
             * 测试事务
             */
            int e = bookRepository.editBookHandler(name, id);
            //int i = 1/0;
            int r = bookRepository.removeBookHandler(id+1);
            return e+r;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
