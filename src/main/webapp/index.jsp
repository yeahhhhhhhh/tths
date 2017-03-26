<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

    <form action="${pageContext.request.contextPath}/file/fileUpload" method="post" enctype="multipart/form-data">
        <input type="file" name="file1">
        uid:<input type="text" name="uid" value="282">
        <input type="submit" value="上传">
    </form>

    <a href="${pageContext.request.contextPath}/file/fileDownload?fid=3">下载文件</a>

    <form action="${pageContext.request.contextPath}/user/login" method="post">
        用户名：<input type="text" name="username">
        密码：<input type="text" name="password">
        <input type="submit" value="登录">
    </form>

    <form action="${pageContext.request.contextPath}/user/changeInfo" method="post">
        邮箱：<input type="text" name="email">
        电话：<input type="text" name="number">
        uid:<input type="text" name="uid" value="282">
        <input type="submit" value="修改">
    </form>

    <a href="${pageContext.request.contextPath}/user/logout">注销</a>

<form action="${pageContext.request.contextPath}/file/fileDelete" method="post">
    文件fid：<input type="text" name="fid">
    <input type="submit" value="删除该文件">
</form>

</body>
</html>
