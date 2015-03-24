method="POST"
path = "/v1/attribute3"
batch = 10

request  = function()
   headers = {}
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
