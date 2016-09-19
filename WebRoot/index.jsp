<%@ page import="java.io.File" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.io.RandomAccessFile" %>
<%!
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, File file) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        java.io.FileInputStream fis = new java.io.FileInputStream(raf.getFD());
        response.setHeader("Server", "www.trydone.com");
        response.setHeader("Accept-Ranges", "bytes");
        long pos = 0;
        long len;
        len = raf.length();
        if (request.getHeader("Range") != null) {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            pos = Long.parseLong(request.getHeader("Range")
                    .replaceAll("bytes=", "")
                    .replaceAll("-", "")
            );
        }
        response.setHeader("Content-Length", Long.toString(len - pos));
        if (pos != 0) {
            response.setHeader("Content-Range", new StringBuffer()
                    .append("bytes ")
                    .append(pos)
                    .append("-")
                    .append(Long.toString(len - 1))
                    .append("/")
                    .append(len)
                    .toString()
            );
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", new StringBuffer()
                .append("attachment;filename=\"")
                .append(file.getName())
                .append("\"").toString());
        raf.seek(pos);
        byte[] b = new byte[2048];
        int i;
        OutputStream outs = response.getOutputStream();
        while ((i = raf.read(b)) != -1) {
            outs.write(b, 0, i);
        }
        raf.close();
        fis.close();
    }
%>
<%
    String filePath = request.getParameter("file");
    filePath = application.getRealPath(filePath);
    File file = new File(filePath);
    downloadFile(request, response, file);
%>