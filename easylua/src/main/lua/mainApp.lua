--
-- Created by IntelliJ IDEA.
-- User: carl
-- Date: 1/15/15
-- Time: 2:06 PM
-- To change this template use File | Settings | File Templates.
--
local genHeader = require("gen_header");
local fileutil = require("fileutil");
local datetimeutil = require("datetimeutil");

brandCode = "engineering"
secret = "1cfbd04f-0f38-4933-bce0-0a124b820f15"
method="POST"
uri = "/v1/attribute"
params = ""
body = '{"deviceId":"ios-1","profileAttributes":{"loyalty_status":"GOLD"}}'

authToken = genHeader.genAuthToken(brandCode,secret,method,uri, params, body)

fileutil.appendResult("result.lua",authToken)

-- current_time = datetimeutil.format_time(os.time(), "!%H:%M:%S %z", "local")
current_time = datetimeutil.format_time(os.time(), "%x %X", "local")
print(current_time)