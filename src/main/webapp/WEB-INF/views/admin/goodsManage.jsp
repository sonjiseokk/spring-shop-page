<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" href="../../../resources/css/admin/goodsManage.css">

    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
</head>
<body>

<%@include file="../includes/admin/header.jsp" %>
            <div class="admin_content_wrap">
                <div class="admin_content_subject"><span>상품 관리</span></div>
                <div class="goods_table_wrap">
                    <!-- 상품 리스트 O -->
                    <c:if test="${listcheck != 'empty'}">
                        <table class="goods_table">
                            <thead>
                            <tr>
                                <td class="th_column_1">상품 번호</td>
                                <td class="th_column_2">상품 이름</td>
                                <td class="th_column_3">작가 이름</td>
                                <td class="th_column_4">카테고리</td>
                                <td class="th_column_5">재고</td>
                                <td class="th_column_6">등록날짜</td>
                            </tr>
                            </thead>
                            <c:forEach items="${list}" var="list">
                                <tr>
                                    <td><c:out value="${list.id}"></c:out></td>
                                    <td><c:out value="${list.bookName}"></c:out></td>
                                    <td><c:out value="${list.author.authorName}"></c:out></td>
                                    <td><c:out value="${list.category.cateName}"></c:out></td>
                                    <td><c:out value="${list.bookStock}"></c:out></td>
                                    <td><fmt:parseDate value="${ list.createdDate }"
                                                       pattern="yyyy-MM-dd" var="parsedDateTime" type="both" />
                                        <fmt:formatDate pattern="yyyy-MM-dd" value="${ parsedDateTime }" /></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                    <!-- 상품 리스트 X -->
                    <c:if test="${listCheck == 'empty'}">
                        <div class="table_empty">
                            등록된 상품이 없습니다.
                        </div>
                    </c:if>
                </div>

                <!-- 검색 영역 -->
                <div class="search_wrap">
                    <form id="searchForm" action="/admin/goodsManage" method="get">
                        <div class="search_input">
                            <input type="text" name="keyword" value='<c:out value="${pageDto.keyword}"></c:out>'>
                            <input type="hidden" name="pageNum" value='<c:out value="${pageDto.pageable.pageNumber }"></c:out>'>
                            <input type="hidden" name="amount" value='${pageDto.pageable.pageSize}'>
                            <input type="hidden" name="type" value="G">
                            <button class='btn search_btn'>검 색</button>
                        </div>
                    </form>
                </div>

                <!-- 페이지 이름 인터페이스 영역 -->
                <div class="pageMaker_wrap">
                    <ul class="pageMaker">

                        <!-- 이전 버튼 -->
                        <c:if test="${pageDto.pageable.pageNumber >= 1 }">
                            <li class="pageMaker_btn prev">
                                <a href="?page=${pageDto.pageable.pageNumber}">이전</a>
                            </li>
                        </c:if>

                        <!-- 페이지 번호 -->
                        <c:forEach begin="${pageDto.pageable.pageNumber+1 }" end="${limit }" var="num">
                            <li class="pageMaker_btn ${pageDto.pageable.pageNumber + 1 == num ? 'active':''}">
                                <a href="?page=${num}">${num}</a>
                            </li>
                        </c:forEach>

                        <!-- 다음 버튼 -->
                        <c:if test="${pageDto.pageable.pageNumber +1 < limit}">
                            <li class="pageMaker_btn next">
                                <a href="?page=${pageDto.pageable.pageNumber + 2 }">다음</a>
                            </li>
                        </c:if>
                    </ul>
                </div>

                <form id="moveForm" action="/admin/goodsManage" method="get" >
                    <input type="hidden" name="pageNum" value="${pageDto.pageable.pageNumber}">
                    <input type="hidden" name="amount" value="${pageDto.pageable.pageSize}">
                    <input type="hidden" name="keyword" value="${pageDto.keyword}">
                </form>
            </div>
            <div class="clearfix"></div>
<%@include file="../includes/admin/footer.jsp" %>


<script>
    $(document).ready(function(){

        let eResult = '<c:out value="${enroll_result}"/>';

        checkResult(eResult);

        function checkResult(result){

            if(result === ''){
                return;
            }

            alert("상품'"+ eResult +"'을 등록하였습니다.");

        }

    });
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
    //     moveForm.find("input[name='pageNum']").val($(this).attr("href"));
    //
    //     moveForm.submit();
    //
    // });
</script>
</body>

</html>
