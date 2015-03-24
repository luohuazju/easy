--
-- Created by IntelliJ IDEA.
-- User: carl
-- Date: 1/15/15
-- Time: 1:54 PM
-- To change this template use File | Settings | File Templates.
--

local sha2 = require("sha2");
local base64 = require("base64");

usertype = "API"
version = "1.0"
timestamp = "1"

function genAuthToken(brandCode,secret,method,uri,params,body)
    -- print("Start to calculate the Token with these info=========")
    userid = "admin@" .. brandCode .. ".com"
    -- print("brandCode = " .. brandCode)
    -- print("secret = " .. secret)

    signatureRaw = secret .. timestamp .. userid .. usertype .. uri .. params .. body
    -- print("raw data = " .. signatureRaw)

    encode_first = urlencode(signatureRaw)
    -- print("first encode = " .. encode_first)

    signature = sha2.sha256(encode_first)
    -- print("signature = " .. signature)

    encode_second = "lpauth_user_id=" .. userid .. ",lpauth_user_type=" .. usertype ..  ",lpauth_signature=" .. signature .. ",lpauth_timestamp=" .. timestamp .. ",lpauth_version=" .. version
    -- print("second encode = " .. encode_second)

    base64string = base64.enc(encode_second)

    token =  "LocalpointAuth " .. base64string
    -- print("token = " .. token)
    return token
end

function urlencode(str)
    if (str) then
        str = string.gsub (str, "\n", "\r\n")
        str = string.gsub (str, "([*+@/{\":,])",
            function (c) return string.format ("%%%02X", string.byte(c)) end)
        str = string.gsub (str, "(})",
            function (c) return string.format ("%%%02X", string.byte(c)) end)
        str = string.gsub (str, " ", "+")
    end
    return str
end

return {
    genAuthToken = genAuthToken;
}






