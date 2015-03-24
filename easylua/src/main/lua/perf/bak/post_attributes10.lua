method="POST"
path = "/v1/attribute"
batch = 10

request  = function()
   headers = {}
   headers["Authorization"] = "LocalpointAuth bHBhdXRoX3VzZXJfaWQ9YWRtaW5AZW5naW5lZXJpbmcuY29tLGxwYXV0aF91c2VyX3R5cGU9QVBJLGxwYXV0aF9zaWduYXR1cmU9MzZmNTY2NWQxOTk3NTc2YTY2OTIzY2E5ZDdkMWI3ZjdmNTYyMTE3YjI4ZjA4NmJkNDEyNzhkMDNhNGVjNzAzMSxscGF1dGhfdGltZXN0YW1wPTEsbHBhdXRoX3ZlcnNpb249MS4w"
   headers["Content-Type"] = "application/json"
   device = '{"deviceId":"c3b83f5498cea8fdac75fc3b6ff10a0fa1dd8d8c","profileAttributes":{"loyalty_status":"GOLD"}}'
   body = device
   for i = 2, batch, 1 do
     body = body .. ',' .. device
   end
   body = '[' .. body .. ']'
   -- print(body)
   return wrk.format(method, path, headers, body)
end

response = function(status, headers, body)
   -- print(status)
end
