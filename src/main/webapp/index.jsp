<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

    <form action="${pageContext.request.contextPath}/file/fileUpload" method="post" enctype="multipart/form-data">
        <input type="file" name="file1">
        <input type="submit" value="上传">
    </form>

    <a href="${pageContext.request.contextPath}/file/fileDownload?fid=2">下载文件</a>

</body>
</html>
