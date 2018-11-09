<%--
  Created by IntelliJ IDEA.
  User: alexh
  Date: 09/11/2018
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
    <title>Upload</title>


    <div class="container">
        <h2>
            uppload a file
        </h2>

        <form method="POST" action="/upload">

            <div>
                <input hidden type="text" name="token" id="token" size="40" value="${fn:escapeXml(file.author)}" class="form-control" />
            </div>

            <div>
                <label for="file">Post content</label>
                <input id="file" type="file" name="file" accept="*" value="${fn:escapeXml(file.content)}"/>
            </div>

            <button type="submit">Save</button>
        </form>
    </div>
</head>
<body>

</body>
</html>
