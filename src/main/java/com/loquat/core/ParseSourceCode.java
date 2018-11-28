package com.loquat.core;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 *
 * @author gangwen.xu
 * Date  : 2018/11/27
 * Time  : 上午11:53
 * 类描述 :
 */
public class ParseSourceCode {

    public void parse(String filePath) {
        try {
            CompilationUnit cu = JavaParser.parse(new File(filePath));
            cu.accept(new MethodVisitor(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration method, Void arg) {
            Optional<BlockStmt> block = method.getBody();
            NodeList<Statement> statements = block.get().getStatements();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("st=>start: 开始"+"\r\n");
            stringBuffer.append("e=>end: 结束"+"\r\n");
            for (Statement tmp : statements) {
                tmp.removeComment();
                if (tmp instanceof IfStmt) {
                    IfStmt ifStmt = (IfStmt) tmp;
                    stringBuffer.append("cond1=>condition: 确定"+"\r\n");
                    stringBuffer.append("op1=>operation: "+ifStmt.getThenStmt()+"\r\n");
                    stringBuffer.append("op2=>operation: "+ifStmt.getElseStmt()+"\r\n");

                    stringBuffer.append("st->cond1"+"\r\n");
                    stringBuffer.append("cond1(yes)->op1->e"+"\r\n");
                    stringBuffer.append("cond1(no)->op2"+"\r\n");
                    System.out.printf(""+stringBuffer);
                }
            }
            super.visit(method, arg);
        }
    }

    /*
    模板方法
    st=>start: 微信验证接入
    e=>end: END

    cond1=>condition: echostr == null

    op2_1=>operation: 将token、timestamp、nonce三个参数进行字典序排序
    op2_2=>operation: 将三个参数字符串拼接成一个字符串进行sha1加密
    cond2=>condition: 加密字符串 == signature

    io1=>inputoutput: 返回echostr

    op1=>operation: 获取post，解析xml数据
    op2=>operation: 判断信息内容，处理相关操作
    io2=>inputoutput: post返回数据


    st->cond1
    cond1(yes)->op1->op2->io2->e
    cond1(no)->op2_1(right)->op2_2->cond2
    cond2(no)->e
    cond2(yes)->io1->e
    */
}
