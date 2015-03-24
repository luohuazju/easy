--
-- Created by IntelliJ IDEA.
-- User: carl
-- Date: 1/16/15
-- Time: 3:10 PM
-- To change this template use File | Settings | File Templates.
--
-- Opens a file in read
function appendResult(filename,append)
    -- Opens a file in append mode
    file = io.open(filename, "a")
    -- sets the default output file as test.lua
    io.output(file)
    -- appends a word test to the last line of the file
    io.write(append .. '\n')
    -- closes the open file
    io.close(file)
end

return {
    appendResult = appendResult
}

