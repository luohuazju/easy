local genHeader = require("gen_header");
local fileutil = require("fileutil");

-- info for dev --
-- command to run 
-- wrk -c 100 -t 8 -d 1m --timeout 5m -s ./create_store.lua  https://visithistory.console.dev.digby.com
brandCode = "visithistory"
secret = "deccfd55-04d1-4cfb-9ea3-7b72e00909f0"
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
   if(status ~= 200) then
      fileutil.appendResult("result.lua",body)
   end
end
