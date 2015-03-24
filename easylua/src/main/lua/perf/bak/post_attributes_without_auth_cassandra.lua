method="POST"
path = "/v1/attribute3"

request  = function()
   headers = {}
   headers["Content-Type"] = "application/json"
   body = '[{"deviceId":"c3b83f5498cea8fdac75fc3b6ff10a0fa1dd8d8c","profileAttributes":{"loyalty_status":"GOLD"}}]'
   return wrk.format(method, path, headers, body)
end

response = function(status, headers, body)
    -- print(status)
end
