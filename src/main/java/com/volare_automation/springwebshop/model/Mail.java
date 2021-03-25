package com.volare_automation.springwebshop.model;

import java.util.List;
import java.util.Map;

public class Mail {


    private String to;

    public void setHtmlTemplate(HtmlTemplate htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    private String from;
    private String subject;
    private HtmlTemplate htmlTemplate;

    public static class HtmlTemplate{

        private String template;
        private Map<String, Object> props;
        //private List<Products> props;

        public HtmlTemplate(String template, Map<String, Object> props) {
            this.template = template;
            this.props = props;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

//        public List<Products> getProps() {
//            return props;
//        }
//
//        public void setProps(List<Products> props) {
//            this.props = props;
//        }
        public Map<String, Object> getProps() {
            return props;
        }

        public void setProps(Map<String, Object> props) {
            this.props = props;
        }
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public HtmlTemplate getHtmlTemplate() {
        return htmlTemplate;
    }
}
