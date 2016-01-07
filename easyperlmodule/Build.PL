use strict;
use warnings;
use Module::Build;

my $builder = Module::Build->new(
    module_name         => 'HelloWorld',
    license             => 'perl',
    dist_abstract       => 'HelloWorld short description',
    dist_author         => 'Author Name <luohuazju@gmail.com>',
    build_requires => {
        'Test::More' => '0.10',
    },
);

$builder->create_build_script();

