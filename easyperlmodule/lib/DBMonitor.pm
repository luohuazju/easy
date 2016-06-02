use strict;
use warnings;
package lib::DBMonitor;

$DBMonitor::VERSION = '0.1';

__PACKAGE__->run( @ARGV ) unless caller();

sub run { 
  print "I'm a script!\n"; 
  my $result = &hello();
  print "result is " . $result . "\n";
}

sub hello {
   return "Hello, DBMonitor, World!";
}

1;

__END__