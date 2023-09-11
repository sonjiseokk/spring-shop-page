<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="../../../resources/css/admin/authorModify.css">

    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
</head>
<body>
<%@include file="../includes/admin/header.jsp" %>
<div class="admin_content_wrap">
    <div class="admin_content_subject"><span>작가 상세</span></div>
    <div class="admin_content_main">
        <form id="modifyForm" action="/admin/authorModify/${authorInfo.id}" method="post">
            <div class="form_section">
                <div class="form_section_title">
                    <label>작가 번호</label>
                </div>
                <div class="form_section_content">
                    <input class="input_block" name="authorId" readonly value="<c:out value='${authorInfo.id }'></c:out>">
                </div>
            </div>
            <div class="form_section">
                <div class="form_section_title">
                    <label>작가 이름</label>
                </div>
                <div class="form_section_content">
                    <input name="authorName" value="<c:out value='${authorInfo.authorName }'></c:out>" >
                    <span id="warn_authorName">작가 이름을 입력 해주세요.</span>
                </div>
            </div>
            <div class="form_section">
                <div class="form_section_title">
                    <label>소속 국가</label>
                </div>
                <div class="form_section_content">
                    <select class="input_block" name="nation" >
                        <option value="none" selected disabled="disabled">=== 선택 ===</option>
                        <option value="KOREA"  <c:out value=" ${authorInfo.nation eq 'KOREA'? 'selected':''}"/>>국내</option>
                        <option value="ETC"  <c:out value=" ${authorInfo.nation eq 'ETC' ? 'selected':''}"/>>국외</option>
                    </select>
                </div>
            </div>
            <div class="form_section">
                <div class="form_section_title">
                    <label>작가소개</label>
                </div>
                <div class="form_section_content">
                    <textarea name="authorIntro" ><c:out value='${authorInfo.authorIntro }'/></textarea>
                    <span id="warn_authorIntro">작가 소개를 입력 해주세요.</span>
                </div>
            </div>
            <div class="form_section">
                <div class="form_section_title">
                    <label>등록 날짜</label>
                </div>
                <div class="form_section_content">
                    <fmt:parseDate value="${ authorInfo.createdDate }" pattern="yyyy-MM-dd" var="parsedDateTime" type="both" />
                    <input class="input_block" type="text" readonly="readonly" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${ parsedDateTime }" />">
                </div>
            </div>
            <div class="form_section">
                <div class="form_section_title">
                    <label>수정 날짜</label>
                </div>
                <div class="form_section_content">
                    <fmt:parseDate value="${ authorInfo.lastModifiedDate }" pattern="yyyy-MM-dd" var="parsedDateTime" type="both" />
                    <input class="input_block" type="text" readonly="readonly" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${ parsedDateTime }" />">
                </div>
            </div>
            <div class="btn_section">
                <button id="cancelBtn" class="btn">취소</button>
                <button id="modifyBtn" class="btn modify_btn">수 정</button>
                <button id="deleteBtn" class="btn delete_btn">삭 제</button>
            </div>
        </form>
    </div>
</div>

<form id="moveForm" method="get">
    <input type="hidden" name="pageNum" value='<c:out value="${pageDto.pageable.pageNumber }"/>'>
    <input type="hidden" name="amount" value='<c:out value="${pageDto.pageable.pageSize }"/>' >
    <input type="hidden" name="keyword" value='<c:out value="${pageDto.keyword }"/>'>
</form>
<%@include file="../includes/admin/footer.jsp" %>
<script>
    let moveForm = $("#moveForm");
    let modifyForm = $("#modifyForm");


    /* 작가 상세 페이지 이동 버튼 */
    $("#cancelBtn").on("click", function(e){

        e.preventDefault();

        var idValue = ${authorInfo.id};
        moveForm.attr("action", "/admin/authorDetail/" + idValue)
        moveForm.submit();

    });

    /* 작가 수정 버튼 작동 및 유효성 검사 */
    $("#modifyBtn").on("click", function(e){

        let authorName = $(".form_section_content input[name='authorName']").val();
        let authorIntro = $(".form_section_content textarea").val();

        let	nameCk = false;
        let introCk = false;

        e.preventDefault();

        if(!authorName){
            $("#warn_authorName").css("display", "block");
        } else {
            $("#warn_authorName").css("display", "none");
            nameCk = true;
        }
        if(!authorIntro){
            $("#warn_authorIntro").css("display", "block");
        } else {
            $("#warn_authorIntro").css("display", "none");
            introCk = true;
        }

        var idValue = ${authorInfo.id};

        if(nameCk && introCk ){
            modifyForm.submit();
        } else {
            console.log(modifyForm);
            return false;
        }


    });

    /* 삭제 버튼 */
    $("#deleteBtn").on("click", function(e){
        e.preventDefault();
        moveForm.find("input").remove();
        var idValue = ${authorInfo.id};
        moveForm.attr("action", "/admin/authorDelete/" + idValue);
        moveForm.attr("method", "post");
        moveForm.submit();
    });
</script>

</body>
</html>