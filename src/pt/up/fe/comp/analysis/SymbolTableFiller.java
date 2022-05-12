package pt.up.fe.comp.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.javacc.output.Translator.SymbolTable;

import pt.up.fe.comp.ast.ASTNode;
import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.ast.PreorderJmmVisitor;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.Stage;

public class SymbolTableFiller extends PreorderJmmVisitor<SymbolTableBuilder,Integer> {

    private final List<Report> reports;

    public SymbolTableFiller(){
        this.reports = new ArrayList<>();

        addVisit(ASTNode.IMPORT_DECL, this::importDeclVisit);
        addVisit(ASTNode.CLASS_DECL, this::classDeclVisit);
        addVisit(ASTNode.METHOD_DECL, this::methodDeclVisit);
    }

    public List<Report> getReports(){
        return reports;
    }

    private Integer importDeclVisit(JmmNode importDecl, SymbolTableBuilder symbolTable){
        var importString = importDecl.getChildren().stream().map(id->id.get("name")).collect(Collectors.joining("."));

        symbolTable.addImport(importString);
        return 0;
    }
    
    private Integer classDeclVisit(JmmNode classDecl, SymbolTableBuilder symbolTable){
        symbolTable.setClassName(classDecl.get("name"));
        classDecl.getOptional("extends").ifPresent(superClass -> symbolTable.setSuperClass(superClass));

        return 0;
    }

    private Integer methodDeclVisit(JmmNode methodDecl, SymbolTableBuilder symbolTable){

        var methodName = methodDecl.getJmmChild(1).get("name");
        
        if(symbolTable.hasMethod(methodName)){
            reports.add(Report.newError(Stage.SEMANTIC, Integer.valueOf(methodDecl.get("line")), Integer.valueOf(methodDecl.get("col")), "Found duplicated method with signature '" + "methodName" + "'", null));

            return -1;
        }

        var returnType = methodDecl.getJmmChild(0);
        var typeName = returnType.get("name");
        var isArray = returnType.getOptional("isArray").map(isArrayString -> Boolean.valueOf(isArrayString)).orElse(false);

        var params = methodDecl.getChildren().subList(2, methodDecl.getNumChildren()-1);
        symbolTable.addMethod(methodName,new Type(typeName, isArray), Collections.emptyList());
        
        return 0;
    }
}