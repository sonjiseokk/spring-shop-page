
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../../../resources/css/admin/authorPop.css">
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
</head>
<body>
<div class="subject_name_warp">
    <span>작가 선택</span>
</div>
<div class="content_wrap">
    <!-- 게시물 표 영역 -->
    <div class="author_table_wrap">
        <!-- 게시물 O -->
        <c:if test="${listCheck != 'empty'}">
            <div class="table_exist">
                <table class="author_table">
                    <thead>
                    <tr>
                        <td class="th_column_1">작가 번호</td>
                        <td class="th_column_2">작가 이름</td>
                        <td class="th_column_3">작가 국가</td>
                    </tr>
                    </thead>
                    <c:forEach items="${list}" var="list">
                        <tr>
                            <td><c:out value="${list.id}"></c:out> </td>
                            <td>
                                <a class="move" href='<c:out value="${list.id}"/>' data-name='<c:out value="${list.authorName}"/>'>
                                    <c:out value="${list.authorName}"></c:out>
                                </a>
                            </td>
                            <td><c:out value="${list.nation}"></c:out> </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        <!-- 게시물 x -->
        <c:if test="${listCheck == 'empty'}">
            <div class="table_empty">
                등록된 작가가 없습니다.
            </div>
        </c:if>

        <!-- 검색 영역 -->
        <div class="search_wrap">
            <form id="searchForm" action="/admin/authorPop" method="get">
                <div class="search_input">
                    <input type="text" name="keyword" value='<c:out value="${pageDto.keyword}"></c:out>'>
                    <input type="hidden" name="pageNum" value='<c:out value="${pageDto.pageable.pageNumber}"></c:out>'>
                    <input type="hidden" name="amount" value='${pageDto.pageable.pageSize}'>
                    <button class='btn search_btn'>검 색</button>
                </div>
            </form>
        </div>

        <div class="pageMaker_wrap" >
            <ul class="pageMaker">
                <!-- 이전 버튼 -->
                <c:if test="${pageDto.pageable.pageNumber >= 1 }">
                    <li class="pageMaker_btn prev">
                        <a href="?page=${pageDto.pageable.pageNumber}">이전</a>
                    </li>
                </c:if>
                <!-- 페이지 번호 -->
                <c:forEach begin="${pageDto.pageable.pageNumber+1}" end="${limit-1 < 1 ? limit : limit-1}" var="num">
                    <li class="pageMaker_btn ${pageDto.pageable.pageNumber+1 == num ? "active":""}">
                        <a href="?page=${num}">${num}</a>
                    </li>
                </c:forEach>
                <!-- 다음 버튼 -->
                <c:if test="${pageDto.pageable.pageNumber + 1  <= limit}">
                    <li class="pageMaker_btn next">
                        <a href="?page=${pageDto.pageable.pageNumber + 1}">다음</a>
                    </li>
                </c:if>
            </ul>
        </div>
        <form id="moveForm" action="/admin/authorPop" method="get">
            <input type="hidden" name="pageNum" value="${pageDto.pageable.pageNumber}">
            <input type="hidden" name="amount" value="${pageDto.pageable.pageSize}">
            <input type="hidden" name="keyword" value="${pageDto.keyword}">
        </form>


    </div>

</div>
<script>

    let searchForm = $('#searchForm');
    let moveForm = $('#moveForm');

    /* 작거 검색 버튼 동작 */
    $("#searchForm button").on("click", function(e){

        e.preventDefault();

        /* 검색 키워드 유효성 검사 */
        if(!searchForm.find("input[name='keyword']").val()){
            alert("키워드를 입력하십시오");
            return false;
        }

        searchForm.find("input[name='pageNum']").val("1");

        searchForm.submit();

    });


    /* 페이지 이동 버튼 */
    // $(".pageMaker_btn a").on("click", function(e){
    //
    //     e.preventDefault();
    //
    //     console.log($(this).attr("href"));
    //
    //     moveForm.find("input[name='pageNum']").val($(this).attr("href"));
    //
    //     moveForm.submit();
    //
    // });

    /* 작가 선택 및 팝업창 닫기 */
    $(".move").on("click", function(e){

        e.preventDefault();

        let authorId = $(this).attr("href");
        let authorName= $(this).data("name");
        $(opener.document).find("#authorId_input").val(authorId);
        $(opener.document).find("#authorName_input").val(authorName);

        window.close();

    });

</script>
</body>
</html>
