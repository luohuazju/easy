method="POST"
path = "/v1/attribute4"

request  = function()
   headers = {}
   headers["Authorization"] = "6f1a7472-f94f-4236-b89b-f177756cd9ba"
   headers["Content-Type"] = "application/json"
   number = math.random(3000000)
   body = '{"deviceId":"ios-' .. number .. '","profileAttributes":{"loyalty_status":"GOLD"}}'
   -- print(body)
   return wrk.format(method, path, headers, body)
end

response = function(status, headers, body)
    --print(status)
end
