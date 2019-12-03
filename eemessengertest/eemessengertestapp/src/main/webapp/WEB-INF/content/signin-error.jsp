<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>EE Messenger</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/font-awesome/5.11.2/css/all.min.css">
    <link rel="shortcut icon" href="https://sigma.software/sites/default/files/favicon.png" type="image/png" />
</head>
<body class="max-vh-100 overflow-hidden">
<div class="container d-flex align-items-center justify-content-center">
    <div class="d-flex justify-content-center">
        <div class="card">
            <div class="card-header">
                <h3 class="card-title text-center">Authentication Error</h3>
            </div>
            <div class="card-body text-center text-light">
                     Invalid username and/or password.
            </div>
            <div class="card-footer">
                <div class="d-flex justify-content-center links">
                    <p><a href="${pageContext.request.contextPath}/send-message/page"> Try again?</a></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
