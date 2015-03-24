method="POST"
path = "/v1/attribute"

request  = function()
   headers = {}
   headers["Authorization"] = "LocalpointAuth bHBhdXRoX3VzZXJfaWQ9YWRtaW5Ac2lsbHljYXQuY29tLGxwYXV0aF91c2VyX3R5cGU9QVBJLGxwYXV0aF9zaWduYXR1cmU9OTM2MDUwYmZjYTg5Yjg4MzlmYmFmNWI4OTYxMjQ0YjNkYmE5NTNmOTE2MDAzMzM2MzM4MzQwYzJlNzY3ZGIxNixscGF1dGhfdGltZXN0YW1wPTEsbHBhdXRoX3ZlcnNpb249MS4w"
   headers["Content-Type"] = "application/json"
   number = math.random(3000000)
   body = '{"deviceId":"ios-' .. number .. '","profileAttributes":{"loyalty_status":"GOLD"}}'
   -- print(body)
   return wrk.format(method, path, headers, body)
end

response = function(status, headers, body)
  -- print(status)
end
