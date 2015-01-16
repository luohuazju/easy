--
-- Created by IntelliJ IDEA.
-- User: carl
-- Date: 8/18/14
-- Time: 4:38 PM
-- To change this template use File | Settings | File Templates.
--

method="GET"
path = "/v1/analytics/visitsPerLocation?period=P7D&startDate=2014-8-11+00:00:00&tag=mall&type=visits"

request  = function()
    wrk.headers["Authorization"] = "LocalpointAuth bHBhdXRoX3VzZXJfaWQ9YWRtaW5Ac2lsbHljYXQuY29tLGxwYXV0aF91c2VyX3R5cGU9QVBJLGxwYXV0aF9zaWduYXR1cmU9NzIzMmYyM2NiZGY3YjRhNmJlZWU2MTYxOGNiYjhlNDlkNmZiN2RhZjIwODFhMjA2NWI2YTljMTUxNTRhNGE1ZixscGF1dGhfdGltZXN0YW1wPTEsbHBhdXRoX3ZlcnNpb249MS4w"
    return wrk.format(method, path)
end

response = function(status, headers, body)
    print(body)
end