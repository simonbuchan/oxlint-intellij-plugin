package com.github.simonbuchan.oxlintIntellijPlugin.lsp

import com.intellij.javascript.nodejs.interpreter.NodeJsInterpreterManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServer
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.lsWidget.LspServerWidgetItem

@Suppress("UnstableApiUsage")
class OxlintServerSupportProvider : LspServerSupportProvider {
    override fun fileOpened(
        project: Project,
        file: VirtualFile,
        serverStarter: LspServerSupportProvider.LspServerStarter
    ) {
        val node = NodeJsInterpreterManager.getInstance(project).interpreterRef
        val descriptor = OxlintServerDescriptor(project, node)
        if (descriptor.isSupportedFile(file)) {
            serverStarter.ensureServerStarted(descriptor)
        }
    }

    override fun createLspServerWidgetItem(lspServer: LspServer, currentFile: VirtualFile?): LspServerWidgetItem {
        return LspServerWidgetItem(lspServer, currentFile)
    }
}