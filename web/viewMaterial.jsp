<%@ page import="com.ralph.weixin.domain.Material" %>
<!--

  User: Ralph
  Date: 2016/7/1
  Time: 15:03
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
    <%
      Material m = (Material) request.getAttribute("res");
      if(m == null)
      {
    %>
    <h1>m是空！</h1>
    <%
      }else {
    %>
    <h1><%=m.getTotal_count()%>和<%=m.getItem_count()%></h1>
    <h1><%=m.getErrcode()%>和<%=m.getErrmsg()%></h1>
    <%
      }
    %>
    <table>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Url</th>
      </tr>
      <%
        if(m != null && m.getItem()!=null && m.getItem().size()>0)
        {
          for(int i=0;i<m.getItem().size();i++)
          {
            Material.ItemBean obj = m.getItem().get(i);
      %>
            <tr>
              <td><%=obj.getMedia_id()%></td>
              <td><%=obj.getName()%></td>
              <td><%=obj.getUrl()%></td>
            </tr>
      <%
          }
        }
      %>
    </table>
  </body>
</html>
