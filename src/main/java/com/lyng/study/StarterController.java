package com.lyng.study;

import com.lyng.study.domain.Book;
import com.lyng.study.domain.BookVo;
import com.lyng.study.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Administrator on 2017/11/13.
 */

/**
 * @RestController 注解=@Controller+@ResponseBody
 * 相当于此类中的每个方法返回值都会映射成json格式返回
 *
 * RESTful API 模板
 */
@RestController
@RequestMapping("/v1")
public class StarterController {

    /**
     * 自动注入业务逻辑层接口
     */
    @Autowired
    private BookService bookService;

    /**
     * springboot-helloworld
     * @RequestMapping 映射路径
     * 可以根据具体的请求方法,替换为@GetMapping,@PostMapping等;
     * @RequestMapping 可以接收任何方法的请求
     * @return
     */
    //@RequestMapping("/sayHello")
    @GetMapping("/sayHello")
    public String sayHello(){
        return "hello world";
    }

    /**
     * RESTful api-获得全部书籍
     * GET 方式
     * @RequestMapping(value = "/books",method = RequestMethod.GET)
     * 相当于 @GetMapping
     * @return
     */
    //@RequestMapping(value = "/books",method = RequestMethod.GET)
    @GetMapping("/books")
    public Object getBooks(){
        //return "books list";
        return bookService.findAll();
    }

    /**
     * RESTful api-新增书籍
     * POST方式
     * @RequestMapping(value = "/books",method = RequestMethod.POST)
     * 相当于 @PostMapping
     * @return
     */
    //@RequestMapping(value = "/books",method = RequestMethod.POST)
    @PostMapping("/books")
    public Object saveBook(@RequestParam String name,
                           @RequestParam String author,
                           @RequestParam(required = false,defaultValue = "0") int status,
                           @RequestParam("desc") String description){
//        Book book = new Book();
//        book.setId(System.currentTimeMillis());
//        book.setName("<<笑傲江湖>>");
//        book.setAuthor("金庸");
//        book.setStatus(0);
//        book.setDescription("这是一部很好看的武侠小说");


        /**
         * 从前台页面获取相应值
         */
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setStatus(status);
        book.setDescription(description);
        Book rstBook = bookService.save(book);
        //return "save books";
        return rstBook;
    }

    /**
     * RESTful api-更新书籍
     * PUT方式
     * @RequestMapping(value = "/books",method = RequestMethod.PUT)
     * 相当于 @PutMapping
     * @return
     */
    //@RequestMapping(value = "/books",method = RequestMethod.PUT)
    @PutMapping("/books")
    public Object editBook(@RequestParam String name,
                           @RequestParam long id,
                           @RequestParam String author,
                           @RequestParam(required = false,defaultValue = "0") int status,
                           @RequestParam("desc") String description){
        /**
         * 更新底层走的也是save方法,如果有传的id主键,则会走更新,反之,走新增
         * 但是这里需要注意的是:更新实体的时候,除了需要加入主键id,还需要将实体中保存时的其他属性值填充,
         * 不然的话,会将已保存的属性值置空;
         *
         * 后期替换方案为自定义更新
         */
//        Book book = new Book();
//        book.setId(3);
//        book.setName("鹿鼎记");
//        book.setAuthor("金庸");
//        book.setStatus(0);
//        book.setDescription("这是一部很好看的武侠小说");
//        Book rstBook = bookService.save(book);


        /**
         * 从前台页面获取相应值
         */
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setStatus(status);
        book.setDescription(description);
        Book rstBook = bookService.save(book);
        //return "edit books";
        return rstBook;
    }

    /**
     * RESTful api-删除书籍
     * DELETE方式
     * @RequestMapping(value = "/books",method = RequestMethod.DELETE)
     * 相当于 @DeleteMapping
     * @return
     */
    //@RequestMapping(value = "/books",method = RequestMethod.DELETE)
    @DeleteMapping("/books/{id}")
    public void removeBook(@PathVariable long id){
        //return "remove books";
        bookService.removeBook(id);
    }

    /**
     * RESTful api-获取书籍详情
     * GET方式
     * @PathVariable 注解获取url中所带参数,如果接收key和url中的key一样,则不需要再显示指定
     * {id} 不一定放在url串最后,可以放到url串中间等,这里习惯放置最后;
     * {id:正则} 用于过滤符合正则表达式的url,保证方法安全性 {id:[0-9]}
     * @return
     */
    @GetMapping("/books/{id}")
    public Object getBookDetail(@PathVariable("id") long bid){
        //return bid+"book detail";
        return bookService.findOne(bid);
    }

    /**
     * 获取全部书单(分页)
     *  参数:page默认0(代表第一页),size默认3
     * @return
     */
    @GetMapping("/booksByPage")
    //public Page<Book> getBooksByPage(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size) {
    public Page<Book> getBooksByPage(@PageableDefault(size = 3,
            direction = Sort.Direction.DESC,sort = {"id"}) Pageable pageable) {
        //Sort sort = new Sort(Sort.Direction.DESC,"id");
        //Pageable pageable = new PageRequest(page,size,sort);

        return bookService.getBooksByPage(pageable);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-单个字段
     * @return
     */
    @GetMapping("/books/getBookBy")
    public List<Book> findBy(@RequestParam String author){
        return bookService.findByAuthor(author);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-And
     * @return
     */
    @PostMapping("/books/getBookByAnd")
    public List<Book> findByAnd(@RequestParam String author,
                                @RequestParam int status){
        return bookService.findByAuthorAndStatus(author,status);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-Or
     * 通过实体对象接收
     * @return
     */
    @PostMapping("/books/getBookByOr")
    public List<Book> findByOr(Book book){
        return bookService.findByAuthorOrStatus(book.getAuthor(),book.getStatus());
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-Between
     * @return
     */
    @PostMapping("/books/getBookByBetween")
    public List<Book> findByBetween(@RequestParam int start,
                                    @RequestParam int end){
        return bookService.findByStatusBetween(start,end);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-LessThan
     * @return
     */
    @PostMapping("/books/getBookByLessThan")
    public List<Book> findByLessThan(@RequestParam int status){
        return bookService.findByStatusLessThan(status);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-LessThanEqual
     * @return
     */
    @PostMapping("/books/getBookByLessThanEqual")
    public List<Book> findByLessThanEqual(@RequestParam int status){
        return bookService.findByStatusLessThanEqual(status);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-Before
     * @return
     */
    @PostMapping("/books/getBookByBefore")
    public List<Book> findByBefore(@RequestParam int status){
        return bookService.findByStatusBefore(status);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-After
     * @return
     */
    @PostMapping("/books/getBookByAfter")
    public List<Book> findByAfter(@RequestParam int status){
        return bookService.findByStatusAfter(status);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-GreaterThan
     * @return
     */
    @PostMapping("/books/getBookByGreaterThan")
    public List<Book> findByGreaterThan(@RequestParam int status){
        return bookService.findByStatusGreaterThan(status);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-GreaterThanEqual
     * @return
     */
    @PostMapping("/books/getBookByGreaterThanEqual")
    public List<Book> findByGreaterThanEqual(@RequestParam int status){
        return bookService.findByStatusGreaterThanEqual(status);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-IsNull
     * @return
     */
    @GetMapping("/books/getBookByIsNull")
    public List<Book> findByIsNull(){
        return bookService.findByAuthorIsNull();
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-IsNotNull
     * @return
     */
    @GetMapping("/books/getBookByIsNotNull")
    public List<Book> findByIsNotNull(){
        return bookService.findByAuthorIsNotNull();
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-Like
     * 这里的参数得用%或者_包装
     * @return
     */
    @PostMapping("/books/getBookByLike")
    public List<Book> findByLike(@RequestParam String desc){
        desc = "%"+desc+"%";
        return bookService.findByDescriptionLike(desc);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-NotLike
     * @return
     */
    @PostMapping("/books/getBookByNotLike")
    public List<Book> findByNotLike(@RequestParam String desc){
        desc = "%"+desc+"%";
        return bookService.findByDescriptionNotLike(desc);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-StartingWith
     * @return
     */
    @PostMapping("/books/getBookByStartingWith")
    public List<Book> findByStartingWith(@RequestParam String name){
        name = name+"%";
        return bookService.findByNameStartingWith(name);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-EndingWith
     * @return
     */
    @PostMapping("/books/getBookByEndingWith")
    public List<Book> findByEndingWith(@RequestParam String name){
        name = "%"+name;
        return bookService.findByNameEndingWith(name);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-Containing
     * @return
     */
    @PostMapping("/books/getBookByContaining")
    public List<Book> findByContaining(@RequestParam String name){
        name = "%"+name+"%";
        return bookService.findByNameContaining(name);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-OrderBy
     * @return
     */
    @PostMapping("/books/getBookByOrderBy")
    public List<Book> findByOrderBy(@RequestParam String name){
        return bookService.findByNameOrderByIdDesc(name);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-Not
     * @return
     */
    @PostMapping("/books/getBookByNot")
    public List<Book> findByNot(@RequestParam int status){
        return bookService.findByStatusNot(status);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-In
     * @return
     */
    @PostMapping("/books/getBookByIn")
    public List<Book> findByIn(@RequestParam String ids){
        String[] idsArray = ids.split(",");
        Collection<Integer> status = new ArrayList<>(idsArray.length);
        for (int i = 0;i < idsArray.length;i++){
            status.add(Integer.parseInt(idsArray[i]));
        }
        return bookService.findByStatusIn(status);
    }

    /**
     * jpa-复杂查询(基于命名规则的查询)-NotIn
     * @return
     */
    @PostMapping("/books/getBookByNotIn")
    public List<Book> findByNotIn(@RequestParam String ids){
        String[] idsArray = ids.split(",");
        Collection<Integer> status = new ArrayList<>();
        for (int i = 0;i < idsArray.length;i++){
            status.add(Integer.parseInt(idsArray[i]));
        }
        //status.add(0);
        //status.add(1);
        return bookService.findByStatusNotIn(status);
    }

    /**
     * jpa-自定义查询
     * @param status
     * @return
     */
    @GetMapping("/books/getBookByCustomer")
    public List<BookVo> findByCustomer(@RequestParam int status){
        return bookService.findByCustom(status);
    }

    /**
     * jpa-自定义查询
     * @param nameLen
     * @return
     */
    @GetMapping("/books/getBookEntityByCustomer")
    public List<Book> findEntityByCustome(@RequestParam int nameLen){
        return bookService.findEntityByCustome(nameLen);
    }

    /**
     * jpa-自定义更新
     * @param book
     * @return
     */
    @PostMapping("/books/bookHandler/{action}")
    public Map bookHandler(Book book, @PathVariable String action){
        Map map = new HashMap();
        int rst = 0;
        if("update".equals(action)){
            rst = bookService.editBookHandler(book.getName(), book.getId());
        }
        if(rst>0){
            map.put("flag",true);
            map.put("msg","操作成功");
        }
        return map;
    }

}
