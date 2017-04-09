<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

    <form action="${pageContext.request.contextPath}/file/fileUpload" method="post" enctype="multipart/form-data">
        <input type="file" name="file1">
        uid:<input type="text" name="uid" value="282">
        headImgOrFile:<input type="text" name="headImgOrFile" value="head_img">
        <input type="submit" value="上传">
    </form>
    <hr/>

    <a href="${pageContext.request.contextPath}/file/fileDownload?fid=3">下载文件</a>
    <hr/>

    <form action="${pageContext.request.contextPath}/user/login" method="post">
        用户名：<input type="text" name="username">
        密码：<input type="text" name="password">
        <input type="submit" value="用户登录">
    </form>
    <hr/>

    <form action="${pageContext.request.contextPath}/user/adminLogin" method="post">
        用户名：<input type="text" name="username">
        密码：<input type="text" name="password">
        <input type="submit" value="管理员登录">
    </form>
    <hr/>

    <form action="${pageContext.request.contextPath}/user/changeInfo" method="post">
        邮箱：<input type="text" name="email">
        电话：<input type="text" name="number">
        uid:<input type="text" name="uid" value="282">
        <input type="submit" value="修改">
    </form>
    <hr/>

    <a href="${pageContext.request.contextPath}/user/logout">注销</a>
    <hr/>

    <form action="${pageContext.request.contextPath}/file/fileDelete" method="post">
        文件fid：<input type="text" name="fid">
        <input type="submit" value="删除该文件">
    </form>
    <hr/>

    <form action="${pageContext.request.contextPath}/user/findTeachers" method="post">
        <input type="text" name="nowPage" value="1">
        <input type="text" name="listCount" value="2">
        <input type="text" name="teacher_title" value="副教授">
        <input type="submit" value="查找教师">
    </form>
    <form action="${pageContext.request.contextPath}/user/findTeachers" method="post">
    <input type="text" name="nowPage" value="1">
    <input type="text" name="listCount" value="2">
    <input type="text" name="username" value="ceshi">
    <input type="submit" value="按名字查找教师">
    </form>
    <form action="${pageContext.request.contextPath}/admin/leftTeachers" method="post">
        nowPage:<input type="text" name="nowPage" value="1">
        listCount:<input type="text" name="listCount" value="2">
        uid:<input type="text" name="uid" value="1">
        <input type="submit" value="查找已辞职教师">
    </form>
    <form action="${pageContext.request.contextPath}/admin/leftTeachers" method="post">
        nowPage:<input type="text" name="nowPage" value="1">
        listCount:<input type="text" name="listCount" value="2">
        username:<input type="text" name="username" value="s">
        uid:<input type="text" name="uid" value="1">
        <input type="submit" value="按名字查找已辞职教师">
    </form>
    <hr/>

    <form action="${pageContext.request.contextPath}/user/changePassword" method="post">
        用户名：<input type="text" name="username">
        旧密码：<input type="text" name="password">
        新密码：<input type="text" name="newPassword">
        确认密码：<input type="text" name="newPassword2">
        <input type="submit" value="修改密码">
    </form>
    <hr/>

    <form action="${pageContext.request.contextPath}/admin/addNewTeachers" method="post">
        老师名字：<input type="text" name="username">
        管理员id：<input type="text" name="uid" value="1">
        <input type="submit" value="添加老师">
    </form>
    <hr/>

    <form action="${pageContext.request.contextPath}/user/valiTeacher" method="post">
        老师名字：<input type="text" name="username">
        <input type="submit" value="验证老师是否存在">
    </form>
    <br/>
    <form action="${pageContext.request.contextPath}/user/sendMail" method="post">
        老师名字：<input type="text" name="username">
        邮箱：<input type="text" name="email">
        <input type="submit" value="发送邮箱">
    </form>
    <br/>
    <form action="${pageContext.request.contextPath}/user/resetPassword" method="post">
        老师名字：<input type="text" name="username" value="${param.username}">
        数字签名：<input type="text" name="validatacode" value="${param.validatacode}">
        新密码:<input type="text" name="newPassword">
        确认新密码:<input type="text" name="newPassword2">
        <input type="submit" value="设置新密码">
    </form>
    <hr/>

    <form action="${pageContext.request.contextPath}/user/findTeacherType" method="post">
        typeName：<input type="text" name="typeName" value="teacher_job">
        <input type="submit" value="查找教师teacher_job">
    </form>
    <form action="${pageContext.request.contextPath}/user/findTeacherType" method="post">
        typeName：<input type="text" name="typeName" value="teacher_title">
        <input type="submit" value="查找教师teacher_title">
    </form>
    <hr/>

    <form action="${pageContext.request.contextPath}/admin/changeTeacherType" method="post">
        typeName：<input type="text" name="typeName" value="teacher_title">
        addOrDelete：<input type="text" name="addOrDelete" value="add">
        newType：<input type="text" name="newType" value="添加title1">
        <input type="submit" value="修改config">
    </form>
    <form action="${pageContext.request.contextPath}/admin/changeTeacherType" method="post">
        typeName：<input type="text" name="typeName" value="teacher_title">
        addOrDelete：<input type="text" name="addOrDelete" value="delete">
        newType：<input type="text" name="newType" value="添加title1">
        <input type="submit" value="修改config">
    </form>
    <hr/>

    <form action="${pageContext.request.contextPath}/admin/deleteTeachers" method="post">
        typeName：<input type="text" name="username" value="ceshi">
        addOrDelete：<input type="text" name="addOrDelete" value="delete">
        type：<input type="text" name="type" value="leave">
        <input type="submit" value="删除教师">
    </form>
    <hr/>

    <form action="${pageContext.request.contextPath}/file/fileList" method="post">
        uid：<input type="text" name="uid" value="282">
        <input type="submit" value="文件list">
    </form>
</body>
</html>
