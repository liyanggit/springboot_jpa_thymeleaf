package com.lyng.study;

import com.lyng.study.domain.Book;
import com.lyng.study.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/13.
 */

/**
 *@Controller 注解 标注普通的web控制器
 */
@Controller
@RequestMapping("/web")
public class WebController {

    /**
     * 自动注入业务逻辑层接口
     */
    @Autowired
    private BookService bookService;

    /**
     * @Value 注解会从属性文件中找到${}定义的值,
     * 并自动注入到当前属性中,供当前类调用
     */
    @Value("${book.id}")
    private long id;
    @Value("${book.name}")
    private String name;
    @Value("${book.author}")
    private String author;
    @Value("${book.status}")
    private int status;

    /**
     *  @Autowired 注解自动注入实体或属性
     */
    //@Autowired
    //private Book book;

    /**
     * @ResponseBody 注解的方法,会将返回值映射成json格式返回
     * @return
     */
    @RequestMapping("/sayHello")
    @ResponseBody
    public String sayHello(){
        return "helloworld";
    }

    /**
     * 没有@ResponseBody 注解的方法,会返回定义的字符串模板
     * 模板一般放在resources/templates目录下
     * 若映射到此模板,需要添加thymeleaf依赖(相当于springMVC中的视图解析器-viewResolver)
     * @return
     */
    @RequestMapping("/toHello")
    public String toHello(){
        return "hello";
    }

    /**
     * @RequestParam 注解,用于接收请求中的参数,
     * 相当于 request.getParameter("XXX");
     * 如果参数过多,推荐用实体接收;
     * 如果某个参数为空,可以设置默认值(defaultValue)或不是必须的(required)
     * @param bid
     * @param bname
     * @param author
     * @return
     */
    @RequestMapping("/saveObject")
    @ResponseBody
    public Map saveObject(@RequestParam("id") long bid,
                          @RequestParam("name") String bname,
                          @RequestParam String author,
                          @RequestParam(required = false,defaultValue = "0") int status){
        Map map = new HashMap();
        map.put("id",bid);
        map.put("name",bname);
        map.put("author",author);
        map.put("status",status);
        return map;
    }

    /**
     * 获取书单详情-从属性文件中加载具体值
     * @return
     */
    @RequestMapping("/getBookDetail")
    @ResponseBody
    public Object getBookDetail(){
//        Map bookMap = new HashMap();
//        bookMap.put("bid",id);
//        bookMap.put("name",name);
//        bookMap.put("author",author);
//        bookMap.put("status",status);
//        return bookMap;

        /**
         * 通过实体类接收属性文件中自定义的属性值
         */
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setStatus(status);
        return book;

        /**
         * 通过实体类绑定属性文件中的属性值
         */
        //return book;
    }

    /**
     * 获取书单详情,并显示到前台页面
     * @param bid
     * @param model
     * @return
     */
    @GetMapping("/books/{id}")
    public String getBookDetail(@PathVariable("id") long bid, Model model){
        //return bid+"book detail";
        Book book = bookService.findOne(bid);
        if(book == null){
            book = new Book();
        }
        model.addAttribute("book",book);
        return "book";
    }

    /**
     * 获取全部书单
     * @return
     */
    @GetMapping("/books")
    public String getBooks(Model model){
        //return "books list";
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList",bookList);
        return "books";
    }

}
