use strict;
use warnings;
use Test::More qw(no_plan);

# Verify module can be included via "use" pragma
BEGIN { use_ok('DBMonitor') };

# Verify module can be included via "require" pragma
require_ok( 'DBMonitor' );

# Test hello() routine using a regular expression
my $helloCall = DBMonitor::hello();
like($helloCall, qr/Hello, .*World/, "hello() RE test");