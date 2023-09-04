<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="../resources/css/admin/authorManage.css">

    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
</head>
</head>
<body>

<%@include file="../includes/admin/header.jsp" %>
            <div class="admin_content_wrap">
                <div class="admin_content_subject"><span>작가 관리</span></div>
                <div class="author_table_wrap">
                    <!-- 게시물 O -->
                    <c:if test="${listCheck != 'empty' }">
                    <table class="author_table">
                        <thead>
                        <tr>
                            <td class="th_column_1">작가 번호</td>
                            <td class="th_column_2">작가 이름</td>
                            <td class="th_column_3">작가 국가</td>
                            <td class="th_column_4">등록 날짜</td>
                            <td class="th_column_5">수정 날짜</td>
                        </tr>
                        </thead>
                        <c:forEach items="${list}" var="list">
                            <tr>
                                <td><c:out value="${list.id}"></c:out> </td>
                                <td>
                                    <a class="move" href='<c:out value="/admin/authorDetail/${list.id}"/>'>
                                        <c:out value="${list.authorName}"></c:out>
                                    </a>
                                </td>
                                <td><c:out value="${list.nation}"></c:out> </td>
<%--                                <td><fmt:formatDate value="${list.createdDate}" pattern="yyyy-MM-dd"/></td>--%>
<%--                                <td><fmt:formatDate value="${list.lastModifiedDate}" pattern="yyyy-MM-dd"/></td>--%>
                                <td><fmt:parseDate value="${ list.createdDate }"
                                               pattern="yyyy-MM-dd" var="parsedDateTime" type="both" />
                                <fmt:formatDate pattern="yyyy-MM-dd" value="${ parsedDateTime }" /></td>
                                <td><fmt:parseDate value="${ list.lastModifiedDate }"
                                               pattern="yyyy-MM-dd" var="parsedDateTime" type="both" />
                                <fmt:formatDate pattern="yyyy-MM-dd" value="${ parsedDateTime }" /></td>
                            </tr>
                        </c:forEach>
                    </table>
                    </c:if>
                    <c:if test="${listCheck == 'empty'}">
                        <div class="table_empty">
                            등록된 작가가 없습니다.
                        </div>
                    </c:if>
                </div>
                <!-- 검색 영역 -->
                <div class="search_wrap">
                    <form id="searchForm" action="/admin/authorManage" method="get">
                        <div class="search_input">
                            <input type="text" name="keyword" value='<c:out value="${pageMaker.keyword}"></c:out>'>
                            <input type="hidden" name="pageNum" value='<c:out value="${pageMaker.pageable.pageNumber}"></c:out>'>
                            <input type="hidden" name="amount" value='${pageMaker.pageable.pageSize}'>
                            <button class='btn search_btn'>검 색</button>
                        </div>
                    </form>
                </div>

                <!-- 페이지 이동 인터페이스 영역 -->
                <div class="pageMaker_wrap" >
                    <ul class="pageMaker">
                        <!-- 이전 버튼 -->
                        <c:if test="${pageMaker.pageable.pageNumber > 1 }">
                            <li class="pageMaker_btn prev">
                                <a href="?page=${pageMaker.pageable.pageNumber - 1}">이전</a>
                            </li>
                        </c:if>
                        <!-- 페이지 번호 -->
                        <c:forEach begin="${pageMaker.pageable.pageNumber}" end="${limit-1 < 1 ? limit : limit-1}" var="num">
                            <li class="pageMaker_btn ${pageMaker.pageable.pageNumber == num ? "active":""}">
                                <a href="?page=${num}">${num}</a>
                            </li>
                        </c:forEach>
                        <!-- 다음 버튼 -->
                        <c:if test="${pageMaker.pageable.pageNumber + 1  <= limit}">
                            <li class="pageMaker_btn next">
                                <a href="?page=${pageMaker.pageable.pageNumber + 1}">다음</a>
                            </li>
                        </c:if>
                    </ul>
                </div>
                <form id="moveForm" action="/admin/authorManage" method="get">
                    <input type="hidden" name="pageNum" value="${pageMaker.pageable.pageNumber}">
                    <input type="hidden" name="amount" value="${limit}">
                    <input type="hidden" name="keyword" value="${pageMaker.keyword}">
                </form>
            </div>

<%@include file="../includes/admin/footer.jsp" %>

<script>
    $(document).ready(function(){
        let result = '<c:out value="${enroll_result}"/>';
        checkResult(result);
        function checkResult(result){
            if(result === ''){
                return;
            }
            alert("작가 '${enroll_result}' 을 등록하였습니다.");
        }
        let mresult = '<c:out value="${modify_result}"/>';
        checkmResult(mresult);
        function checkmResult(mresult){

            if(mresult === '1'){
                alert("작가 정보 수정을 완료하였습니다.");
            } else if(mresult === '0') {
                alert("작가 정부 수정을 하지 못하였습니다.")
            }

        }
    });
    let moveForm = $('#moveForm');

    // /* 페이지 이동 버튼 */
    // $(".pageMaker_btn a").on("click", function (e) {
    //
    //     e.preventDefault();
    //
    //     moveForm.find("input[name='pageNum']").val($(this).attr("href"));
    //
    //     moveForm.submit();
    // });

    let searchForm = $('#searchForm');

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

    // /* 작가 상세 페이지 이동 */
    $(".move").on("click", function(e){

        e.preventDefault();
        var hrefValue = $(this).attr("href");
        var idValue = hrefValue.substring(hrefValue.lastIndexOf('/') + 1);

        var moveForm = $("<form>").attr({
            "method": "GET",
            "action": "/admin/authorDetail/" + idValue
        });

        // 현재 페이지의 pageMaker 정보 가져오기
        var currentPageNum = searchForm.find("input[name='pageNum']").val();
        var currentAmount = searchForm.find("input[name='amount']").val();
        var currentKeyword = searchForm.find("input[name='keyword']").val();

        moveForm.find("input[name='pageNum']").val(currentPageNum);
        moveForm.find("input[name='amount']").val(currentAmount);
        moveForm.find("input[name='keyword']").val(currentKeyword);

        moveForm.append("<input type='hidden' name='pageNum' value='" + currentPageNum + "'>");
        moveForm.append("<input type='hidden' name='amount' value='" + currentAmount + "'>");
        moveForm.append("<input type='hidden' name='keyword' value='" + currentKeyword + "'>");
        moveForm.appendTo("body").submit();
    });
</script>
</body>
</html>