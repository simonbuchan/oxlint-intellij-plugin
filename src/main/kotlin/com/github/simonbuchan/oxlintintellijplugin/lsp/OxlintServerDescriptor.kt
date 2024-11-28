package com.github.simonbuchan.oxlintIntellijPlugin.lsp

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.javascript.nodejs.interpreter.NodeCommandLineConfigurator
import com.intellij.javascript.nodejs.interpreter.NodeJsInterpreterRef
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor

@Suppress("UnstableApiUsage")
class OxlintServerDescriptor(project: Project, private val node: NodeJsInterpreterRef) :
    ProjectWideLspServerDescriptor(project, "Oxlint") {
    override fun createCommandLine(): GeneralCommandLine {
        return GeneralCommandLine().apply {
            setWorkDirectory(project.basePath)
            addParameter("node_modules/oxlint/bin/oxc_language_server")
            NodeCommandLineConfigurator.find(node, project)
                .configure(this)
        }
    }

    override fun isSupportedFile(file: VirtualFile): Boolean {
        // Probably a better way, e.g. file.type:
        return when (file.extension) {
            // many, many more...
            "js", "cjs", "mjs", "ts", "tsx" -> {
                true
            }

            else -> false
        };
    }
}