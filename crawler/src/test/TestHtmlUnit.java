//package test;
//
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlForm;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
//import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
//
//import java.io.FileWriter;
//
///**
// * Created by wyz on 2018/2/6.
// */
//public class TestHtmlUnit {
//    public static void main(String[] args) throws Exception {
////        final WebClient webClient = new WebClient();
////        webClient.getOptions().setCssEnabled(false);//关闭css
////        webClient.getOptions().setJavaScriptEnabled(true);//关闭js
////        long start = System.currentTimeMillis();
////        final HtmlPage page = webClient.getPage("https://www.baidu.com");
////        System.out.println(page.asText());
////        System.out.println((System.currentTimeMillis() - start) / 1000);
////        webClient.close();
//        submittingForm();
//    }
//
//    public static void submittingForm() throws Exception {
//        final WebClient webClient = new WebClient();
//        webClient.getOptions().setCssEnabled(false);//关闭css
//
//        // Get the first page
//        final HtmlPage page1 = webClient.getPage("http://www.baidu.com/");
//
//        // Get the form that we are dealing with and within that form,
//        // find the submit button and the field that we want to change.
//        final HtmlForm form = page1.getFormByName("f");
//
//        final HtmlSubmitInput button = form.getInputByValue("百度一下");
//        final HtmlTextInput textField = form.getInputByName("wd");
//
//        // Change the value of the text field
//        textField.setValueAttribute("htmlUnit");
//
//        // Now submit the form by clicking the button and get back the second
//        // page.
//        final HtmlPage page2 = button.click();
//        for (int i = 0; i < 20; i++) {
//            if (!page2.getByXPath("//div[@class=‘c-gap-top c-recommend‘]").isEmpty()) {
//                break;
//            }
//            synchronized (page2) {
//                page2.wait(500);
//            }
//        }
//        FileWriter writer = new FileWriter("baidu.html");
//        writer.write(page2.asXml());
//        writer.close();
//        // System.out.println(page2.asXml());
//
//        webClient.close();
//    }
//}
