method="POST"
path = "/v1/attribute2"

request  = function()
   headers = {}
   headers["Authorization"] = "LocalpointAuth bHBhdXRoX3VzZXJfaWQ9YWRtaW5AZW5naW5lZXJpbmcuY29tLGxwYXV0aF91c2VyX3R5cGU9QVBJLGxwYXV0aF9zaWduYXR1cmU9NDQ2MTRkZGQyNGU5YzZhZGVjOTE1YjQ1NjQyZTdmYzRiMGJiYWZiYTJmMTBmN2NmYzI5YzI2ZDFlNmM4NjU5NyxscGF1dGhfdGltZXN0YW1wPTEsbHBhdXRoX3ZlcnNpb249MS4w"
   headers["Content-Type"] = "application/json"
   body = '[{"deviceId":"c3b83f5498cea8fdac75fc3b6ff10a0fa1dd8d8c","profileAttributes":{"loyalty_status":"GOLD"}}]'
   return wrk.format(method, path, headers, body)
end

response = function(status, headers, body)
    -- print(status)
end
