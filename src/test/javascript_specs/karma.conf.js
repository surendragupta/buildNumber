// Karma configuration
// Generated on Sun Nov 29 2015 14:03:59 GMT+0530 (IST)

module.exports = function (config) {
  config.set({
    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',
    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],
    // list of files / patterns to load in the browser
    files: [
      'src/jquery-1.11.3.min.js',
      'src/jquery-ui.min.js',
      'spec/jasmine-jquery.js',
      'src/HTMLFixtures.js',
    //Code to test
      'src/adminModel.js',
      'src/adminView.js',
      'src/appModel.js',
      'src/appView.js',
    //jasmine testing spec
      'spec/*.js'
    ],
    // list of files to exclude
    exclude: [
    ],
    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
      'src/adminModel.js': 'coverage',
      'src/adminView.js': 'coverage',
      'src/appModel.js': 'coverage',
      'src/appView.js': 'coverage',
    },
    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['dots', 'coverage'],
    // optionally, configure the reporter
    coverageReporter: {
	   dir: './build/reports/coverage',
	   reporters: [
	   { type: 'html', subdir: 'report-html' },
	   { type: 'cobertura', subdir: 'report-cobertura' }
	   ]
      //to generate html code coverage reports
      // type: 'html',
      //to generate cobertura code coverage report
    },
    // web server port
    port: 9876,
    // enable / disable colors in the output (reporters and logs)
    colors: true,
    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,
    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,
    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
	// browsers: ['PhantomJS', 'Chrome','Firefox'],
    browsers: ['PhantomJS'],
    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,
    // Concurrency level
    // how many browser should be started simultanous
    concurrency: Infinity,
    //plugins
    plugins: [
      'karma-jasmine',
      'karma-coverage',
      'karma-chrome-launcher',
      'karma-firefox-launcher',
      'karma-phantomjs-launcher'
    ]
  })
}
