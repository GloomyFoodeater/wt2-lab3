package main.java.by.bsuir.remotearchive.server.dao.impl;

import main.java.by.bsuir.remotearchive.server.dao.StudentInfoDAO;
import main.java.by.bsuir.remotearchive.server.entity.StudentInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentInfoDAOImpl implements StudentInfoDAO {
    private static final String path = "./src/main/resources/students.xml";
    private static final StudentInfoDAOImpl instance = new StudentInfoDAOImpl();
    private final Map<Integer, StudentInfo> studentInfos = new HashMap<>();


    private StudentInfo studentInfoByNodes(NodeList nodes) {
        int id = 0;
        String firstName = "";
        String lastName = "";
        String patronymic = "";
        int group = 0;

        for (int i = 0; i < nodes.getLength(); i++) {
            var currNode = nodes.item(i);
            if (currNode.getNodeType() == Node.ELEMENT_NODE) {
                String text = currNode.getTextContent();
                switch (currNode.getNodeName()) {
                    case "id" -> id = Integer.parseInt(text);
                    case "firstName" -> firstName = text;
                    case "lastName" -> lastName = text;
                    case "patronymic" -> patronymic = text;
                    case "group" -> group = Integer.parseInt(text);
                    default -> throw new IllegalArgumentException("Invalid node found");
                }
            }
        }

        return new StudentInfo(id, firstName, lastName, patronymic, group);
    }

    public Element createNode(Document document, StudentInfo studentInfo) {
        Element element = document.createElement("StudentInfo");

        Element id = document.createElement("id");
        Element firstName = document.createElement("firstName");
        Element lastName = document.createElement("lastName");
        Element patronymic = document.createElement("patronymic");
        Element group = document.createElement("group");

        id.appendChild(document.createTextNode(String.valueOf(studentInfo.getId())));
        firstName.appendChild(document.createTextNode(String.valueOf(studentInfo.getFirstName())));
        lastName.appendChild(document.createTextNode(String.valueOf(studentInfo.getLastName())));
        patronymic.appendChild(document.createTextNode(String.valueOf(studentInfo.getPatronymic())));
        group.appendChild(document.createTextNode(String.valueOf(studentInfo.getGroup())));

        element.appendChild(id);
        element.appendChild(firstName);
        element.appendChild(lastName);
        element.appendChild(patronymic);
        element.appendChild(group);

        return element;
    }

    private StudentInfoDAOImpl() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(path));
            document.getDocumentElement().normalize();
            NodeList nodes = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    var studentInfo = studentInfoByNodes(node.getChildNodes());
                    studentInfos.put(studentInfo.getId(), studentInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StudentInfoDAOImpl getInstance() {
        return instance;
    }

    @Override
    public boolean contains(int id) {
        return studentInfos.containsKey(id);
    }

    @Override
    public List<StudentInfo> getAll() {
        return new ArrayList<>(studentInfos.values());
    }

    @Override
    public void add(StudentInfo studentInfo) {
        studentInfo.setId(studentInfos.keySet().stream().max(Integer::compare).get() + 1);
        studentInfos.put(studentInfo.getId(), studentInfo);
        update();
    }

    @Override
    public void setById(int id, StudentInfo studentInfo) {
        studentInfo.setId(id);
        studentInfos.put(id, studentInfo);
        update();
    }

    @Override
    public void update() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element rootFile = document.createElement("Students");
            for (var studentInfo : getAll()) {
                Element infoElement = createNode(document, studentInfo);
                rootFile.appendChild(infoElement);
            }
            document.appendChild(rootFile);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(path)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

