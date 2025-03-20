module.exports = {
  branches: ['main'], // Adjust if you're using a different branch name
  repositoryUrl: 'https://github.com/rahul-lohra/My-Sample-Library.git',
  plugins: [
    '@semantic-release/commit-analyzer', // Analyze commit messages
    '@semantic-release/release-notes-generator', // Generate release notes
//    '@semantic-release/changelog', // Update the CHANGELOG.md file
    '@semantic-release/github', // Create a GitHub release
    [
      '@semantic-release/exec',
      {
        "prepareCmd":
          "./gradlew updateVersionProperties -PnewVersionName=${nextRelease.version} -PnewVersionCode=${nextRelease.version}",
          "publishCmd": "./gradlew publishToMavenCentral" //replace with your publish task

      }
    ],
    [
      '@semantic-release/git',
      {
//        assets: ['CHANGELOG.md', 'gradle.properties'], // Files to commit
        assets: ['gradle.properties'], // Files to commit
        message: 'chore(release): ${nextRelease.version} [skip ci]\n\n${nextRelease.notes}',
      },
    ],
  ],
  dryRun: false, // Set to true to dump the change logs and version on terminal
};
