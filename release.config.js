module.exports = {
  branches: ['main'], // Adjust if you're using a different branch name
  repositoryUrl: 'https://github.com/rahul-lohra/My-Sample-Library.git',
  plugins: [
    '@semantic-release/commit-analyzer', // Analyze commit messages
    '@semantic-release/release-notes-generator', // Generate release notes
//    '@semantic-release/changelog', // Update the CHANGELOG.md file
    '@semantic-release/github', // Create a GitHub release
    [
      '@semantic-release/git',
      {
        assets: ['CHANGELOG.md', 'gradle.properties'], // Files to commit
        message: 'chore(release): ${nextRelease.version} [skip ci]\n\n${nextRelease.notes}',
      },
    ],
  ],
  dryRun: true, // Set to true to test your configuration
};
