local genHeader = require("gen_header");

-- info for stage --
-- command to run 
-- wrk -c 100 -t 8 -d 1m --timeout 5m -s ./super_auth.lua  https://engineering.api.dev.digby.com
--brandCode = "engineering"
--secret = "1cfbd04f-0f38-4933-bce0-0a124b820f15"
--uri = "/v1/attribute"
--method="POST"

-- info for local --
-- command to run
-- wrk -c 100 -t 8 -d 1m --timeout 5m -s ./local_auth.lua  http://engineering.api.local.digby.com:9090
brandCode = "engineering"
secret = "6f1a7472-f94f-4236-b89b-f177756cd9ba"
uri = "/v1/attribute"
method="POST"


request  = function()
   number = math.random(3000000)
   -- number = 1
   body = '{"deviceId":"ios-' .. number .. '","profileAttributes":{"loyalty_status":"GOLD"}}'

   headers = {}
   authToken = genHeader.genAuthToken(brandCode,secret,method,uri, "", body)
   -- authToken = 'LocalpointAuth bHBhdXRoX3VzZXJfaWQ9YWRtaW5AZW5naW5lZXJpbmcuY29tLGxwYXV0aF91c2VyX3R5cGU9QVBJLGxwYXV0aF9zaWduYXR1cmU9MzRjZjczNjBiZmJmNGI3MzEwNGM5YTg0ZDA4ZDQ5YTE3MTE1MDk1YTc3MjYzMzVhMDQ3NzNmMTI2NjEyNTdmNyxscGF1dGhfdGltZXN0YW1wPTEsbHBhdXRoX3ZlcnNpb249MS4w'
   headers["Authorization"] = authToken
   headers["Content-Type"] = "application/json"
   
   return wrk.format(method, uri, headers, body)
end

response = function(status, headers, body)
   -- print("body = " .. body)
   -- print("status = " .. status)
end
